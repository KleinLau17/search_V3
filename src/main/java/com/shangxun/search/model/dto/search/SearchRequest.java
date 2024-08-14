package com.shangxun.search.model.dto.search;

import com.shangxun.search.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {

    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 类型
     * "entity_table"
     * "channel_entity"
     */
    private String type;

    private static final long serialVersionUID = 1L;
}
