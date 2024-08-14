package com.shangxun.search.job.cycle;

import com.shangxun.search.esdao.ChannelEntityEsDao;
import com.shangxun.search.mapper.ChannelEntityMapper;
import com.shangxun.search.model.dto.channelEntity.ChannelEntityEsDTO;
import com.shangxun.search.model.entity.ChannelEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 增量同步 ChannelEntity 到 es
 */
// todo 取消注释开启任务
@Component
@Slf4j
public class IncSyncChannelEntityToEs {

    @Resource
    private ChannelEntityMapper channelEntityMapper;

    @Resource
    private ChannelEntityEsDao channelEntityEsDao;

    /**
     * 每分钟执行一次
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void run() {
        // 查询近 5 分钟内的数据
        Date fiveMinutesAgoDate = new Date(new Date().getTime() - 5 * 60 * 1000L);
        List<ChannelEntity> channelEntityList = channelEntityMapper.listChannelEntity(fiveMinutesAgoDate);
        if (CollectionUtils.isEmpty(channelEntityList)) {
            log.info("no inc ChannelEntity");
            return;
        }
        List<ChannelEntityEsDTO> channelEntityEsDTOList = channelEntityList.stream()
                .map(ChannelEntityEsDTO::objToDto)
                .collect(Collectors.toList());
        final int pageSize = 500;
        int total = channelEntityEsDTOList.size();
        log.info("IncSyncChannelEntityToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            channelEntityEsDao.saveAll(channelEntityEsDTOList.subList(i, end));
        }
        log.info("IncSyncChannelEntityToEs end, total {}", total);
    }
}
