package com.shangxun.search.model.dto.channelEntity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.shangxun.search.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChannelEntityQueryRequest extends PageRequest implements Serializable {

    /**
     * 数据库编号
     */
    private String id;

    /**
     * 搜索词
     */
    private String searchText;

    //--------------------------

    /**
     * 主体名称
     */
    private String codeName;

    /**
     * 主体类型名称
     */
    private String typeName;

    private static final long serialVersionUID = 1L;
}
