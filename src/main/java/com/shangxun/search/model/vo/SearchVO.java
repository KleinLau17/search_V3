package com.shangxun.search.model.vo;

import lombok.Data;
import org.apache.poi.ss.usermodel.Picture;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合搜索
 */
@Data
public class SearchVO implements Serializable {

    private List<PostVO> postList;

    private List<EntityTableVO> entityTableList;

    private List<ChannelEntityVO> channelEntityList;

    private List<?> dataList;

    private static final long serialVersionUID = 1L;

}
