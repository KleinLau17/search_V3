package com.shangxun.search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shangxun.search.model.dto.channelEntity.ChannelEntityQueryRequest;
import com.shangxun.search.model.entity.ChannelEntity;
import com.shangxun.search.model.vo.ChannelEntityVO;
import com.shangxun.search.service.ChannelEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * ChannelEntity 服务实现
 */
@Service
@Slf4j
public class ChannelEntityDataSource implements DataSource<ChannelEntityVO> {

    @Resource
    private ChannelEntityService entityTableService;
        
    @Override
    public Page<ChannelEntityVO> doSearch(String searchText, long pageNum, long pageSize) {
        ChannelEntityQueryRequest entityTableQueryRequest = new ChannelEntityQueryRequest();
        entityTableQueryRequest.setSearchText(searchText);
        entityTableQueryRequest.setCurrent(pageNum);
        entityTableQueryRequest.setPageSize(pageSize);
        ServletRequestAttributes servletRequestAttributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Page<ChannelEntity> entityTablePage = entityTableService.searchFromEs(entityTableQueryRequest);
        return entityTableService.getChannelEntityVOPage(entityTablePage, request);
    }
}




