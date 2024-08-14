package com.shangxun.search.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shangxun.search.model.dto.channelEntity.ChannelEntityQueryRequest;
import com.shangxun.search.model.dto.entityTable.EntityTableQueryRequest;
import com.shangxun.search.model.entity.ChannelEntity;
import com.shangxun.search.model.entity.EntityTable;
import com.shangxun.search.model.vo.ChannelEntityVO;
import com.shangxun.search.model.vo.EntityTableVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 刘志楷
* @description 针对表【channel_entity】的数据库操作Service
* @createDate 2024-08-14 14:10:26
*/
public interface ChannelEntityService extends IService<ChannelEntity> {

    /**
     * 分页获取帖子封装
     *
     * @param channelEntityPage
     * @param request
     * @return
     */
    Page<ChannelEntityVO> getChannelEntityVOPage(Page<ChannelEntity> channelEntityPage, HttpServletRequest request);

    /**
     * 从 ES 查询
     * @param channelEntityQueryRequest
     * @return
     */
    Page<ChannelEntity> searchFromEs(ChannelEntityQueryRequest channelEntityQueryRequest);

}
