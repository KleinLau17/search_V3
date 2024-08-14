package com.shangxun.search.mapper;

import com.shangxun.search.model.entity.ChannelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shangxun.search.model.entity.EntityTable;

import java.util.Date;
import java.util.List;

/**
* @author 刘志楷
* @description 针对表【channel_entity】的数据库操作Mapper
* @createDate 2024-08-14 14:10:26
* @Entity com.shangxun.search.model.entity.ChannelEntity
*/
public interface ChannelEntityMapper extends BaseMapper<ChannelEntity> {
    /**
     * 查询 ChannelEntity 列表
     */
    List<ChannelEntity> listChannelEntity(Date minGmtModify);
}




