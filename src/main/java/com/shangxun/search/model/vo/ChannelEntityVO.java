package com.shangxun.search.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.google.gson.Gson;
import com.shangxun.search.model.entity.ChannelEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 帖子视图
 */
@Data
public class ChannelEntityVO implements Serializable {

    private final static Gson GSON = new Gson();

    /**
     * 数据库编号
     */
    private String id;

    /**
     * 操作机构ID
     */
    private String companyId;

    /**
     * 群主体
     */
    private String groupEntityCode;

    /**
     * 子主体
     */
    private String entityCode;

    /**
     * 子主体名称
     */
    private String codeName;

    /**
     * 主体类型
     */
    private String entityType;

    /**
     * 主体类型名称
     */
    private String typeName;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;

    /**
     * 包装类转对象
     *
     * @param channelEntityVO
     * @return
     */
    public static ChannelEntity voToObj(ChannelEntityVO channelEntityVO) {
        if (channelEntityVO == null) {
            return null;
        }
        ChannelEntity channelEntity = new ChannelEntity();
        BeanUtils.copyProperties(channelEntityVO, channelEntity);
        return channelEntity;
    }

    /**
     * 对象转包装类
     *
     * @param channelEntity
     * @return
     */
    public static ChannelEntityVO objToVo(ChannelEntity channelEntity) {
        if (channelEntity == null) {
            return null;
        }
        ChannelEntityVO channelEntityVO = new ChannelEntityVO();
        BeanUtils.copyProperties(channelEntity, channelEntityVO);
        return channelEntityVO;
    }
}
