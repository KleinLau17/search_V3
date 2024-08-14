package com.shangxun.search.job.once;

import com.shangxun.search.esdao.ChannelEntityEsDao;
import com.shangxun.search.model.dto.channelEntity.ChannelEntityEsDTO;
import com.shangxun.search.model.entity.ChannelEntity;
import com.shangxun.search.service.ChannelEntityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全量同步 ChannelEntity 到 es
 */
// todo 取消注释开启任务
@Component
@Slf4j
public class FullSyncChannelEntityToEs implements CommandLineRunner {

    @Resource
    private ChannelEntityService channelEntityService;

    @Resource
    private ChannelEntityEsDao channelEntityEsDao;

    @Override
    public void run(String... args) {
        List<ChannelEntity> channelEntityList = channelEntityService.list();
        if (CollectionUtils.isEmpty(channelEntityList)) {
            return;
        }
        List<ChannelEntityEsDTO> channelEntityEsDTOList = channelEntityList.stream().map(ChannelEntityEsDTO::objToDto).collect(Collectors.toList());
        final int pageSize = 500;
        int total = channelEntityEsDTOList.size();
        log.info("FullSyncChannelEntityToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            channelEntityEsDao.saveAll(channelEntityEsDTOList.subList(i, end));
        }
        log.info("FullSyncChannelEntityToEs end, total {}", total);
    }
}
