package com.shangxun.search.model.dto.channelEntity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.google.gson.Gson;
import com.shangxun.search.model.entity.ChannelEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * ChannelEntity ES 包装类
 **/

// todo 取消注释开启 ES（须先配置 ES）
@Document(indexName = "channel_entity")
@Data
public class ChannelEntityEsDTO implements Serializable {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * 数据库编号
     */
    @Id
    private String id;

    /**
     * 子主体名称
     */
    private String codeName;

    /**
     * 主体类型名称
     */
    private String typeName;

    /**
     * 创建时间
     */
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Field(index = false, store = true, type = FieldType.Date, format = {}, pattern = DATE_TIME_PATTERN)
    private Date gmtModify;

    private static final long serialVersionUID = 1L;

    private static final Gson GSON = new Gson();

    /**
     * 对象转包装类
     *
     * @param channelEntity
     * @return
     */
    public static ChannelEntityEsDTO objToDto(ChannelEntity channelEntity) {
        if (channelEntity == null) {
            return null;
        }
        ChannelEntityEsDTO channelEntityEsDTO = new ChannelEntityEsDTO();
        BeanUtils.copyProperties(channelEntity, channelEntityEsDTO);
        return channelEntityEsDTO;
    }

    /**
     * 包装类转对象
     *
     * @param channelEntityEsDTO
     * @return
     */
    public static ChannelEntity dtoToObj(ChannelEntityEsDTO channelEntityEsDTO) {
        if (channelEntityEsDTO == null) {
            return null;
        }
        ChannelEntity channelEntity = new ChannelEntity();
        BeanUtils.copyProperties(channelEntityEsDTO, channelEntity);
        return channelEntity;
    }
}
