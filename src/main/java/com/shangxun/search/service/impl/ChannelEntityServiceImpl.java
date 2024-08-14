package com.shangxun.search.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shangxun.search.constant.CommonConstant;
import com.shangxun.search.mapper.ChannelEntityMapper;
import com.shangxun.search.model.dto.channelEntity.ChannelEntityEsDTO;
import com.shangxun.search.model.dto.channelEntity.ChannelEntityQueryRequest;
import com.shangxun.search.model.entity.ChannelEntity;
import com.shangxun.search.model.vo.ChannelEntityVO;
import com.shangxun.search.service.ChannelEntityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author 刘志楷
* @description 针对表【channel_entity】的数据库操作Service实现
* @createDate 2024-08-14 14:10:26
*/
@Service
@Slf4j
public class ChannelEntityServiceImpl extends ServiceImpl<ChannelEntityMapper, ChannelEntity>
    implements ChannelEntityService{
    @Resource
    private ChannelEntityMapper channelEntityMapper;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public Page<ChannelEntityVO> getChannelEntityVOPage(Page<ChannelEntity> channelEntityPage, HttpServletRequest request) {
        List<ChannelEntity> channelEntityList = channelEntityPage.getRecords();
        Page<ChannelEntityVO> channelEntityVOPage = new Page<>(channelEntityPage.getCurrent(), channelEntityPage.getSize(), channelEntityPage.getTotal());
        if (CollectionUtils.isEmpty(channelEntityList)) {
            return channelEntityVOPage;
        }
        // 填充信息
        List<ChannelEntityVO> channelEntityVOList = channelEntityList.stream().map(channelEntity -> {
            ChannelEntityVO channelEntityVO = ChannelEntityVO.objToVo(channelEntity);
            return channelEntityVO;
        }).collect(Collectors.toList());
        channelEntityVOPage.setRecords(channelEntityVOList);
        return channelEntityVOPage;
    }

    @Override
    public Page<ChannelEntity> searchFromEs(ChannelEntityQueryRequest channelEntityQueryRequest) {
        String id = channelEntityQueryRequest.getId();
        String searchText = channelEntityQueryRequest.getSearchText();
        String codeName = channelEntityQueryRequest.getCodeName();
        String typeName = channelEntityQueryRequest.getTypeName();
        // es 起始页为 0
        long current = channelEntityQueryRequest.getCurrent() - 1;
        long pageSize = channelEntityQueryRequest.getPageSize();
        String sortField = channelEntityQueryRequest.getSortField();
        String sortOrder = channelEntityQueryRequest.getSortOrder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 过滤
        if (id != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("id", id));
        }
        // 按关键词检索
        if (StringUtils.isNotBlank(searchText)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("codeName", searchText));
            boolQueryBuilder.should(QueryBuilders.matchQuery("typeName", searchText));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 按 codeName 检索
        if (StringUtils.isNotBlank(codeName)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("codeName", codeName));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 按 typeName 检索
        if (StringUtils.isNotBlank(typeName)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("typeName", typeName));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 排序
        SortBuilder<?> sortBuilder = SortBuilders.scoreSort();
        if (StringUtils.isNotBlank(sortField)) {
            sortBuilder = SortBuilders.fieldSort(sortField);
            sortBuilder.order(CommonConstant.SORT_ORDER_ASC.equals(sortOrder) ? SortOrder.ASC : SortOrder.DESC);
        }
        // 分页
        PageRequest pageRequest = PageRequest.of((int) current, (int) pageSize);
        // 构造查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
                .withPageable(pageRequest).withSort(sortBuilder).build();
        SearchHits<ChannelEntityEsDTO> searchHits = elasticsearchRestTemplate.search(searchQuery, ChannelEntityEsDTO.class);
        Page<ChannelEntity> page = new Page<>();
        page.setTotal(searchHits.getTotalHits());
        List<ChannelEntity> resourceList = new ArrayList<>();
        // 查出结果后，从 db 获取最新动态数据
        if (searchHits.hasSearchHits()) {
            List<SearchHit<ChannelEntityEsDTO>> searchHitList = searchHits.getSearchHits();
            List<String> channelEntityIdList = searchHitList.stream().map(searchHit -> searchHit.getContent().getId())
                    .collect(Collectors.toList());
            // 从数据库中取出更完整的数据
            List<ChannelEntity> channelEntityList = baseMapper.selectBatchIds(channelEntityIdList);
            if (channelEntityList != null) {
                Map<String, List<ChannelEntity>> idChannelEntityMap = channelEntityList.stream().collect(Collectors.groupingBy(ChannelEntity::getId));
                channelEntityIdList.forEach(channelEntityId -> {
                    if (idChannelEntityMap.containsKey(channelEntityId)) {
                        resourceList.add(idChannelEntityMap.get(channelEntityId).get(0));
                    } else {
                        // 从 es 清空 db 已物理删除的数据
                        String delete = elasticsearchRestTemplate.delete(String.valueOf(channelEntityId), ChannelEntityEsDTO.class);
                        log.info("delete post {}", delete);
                    }
                });
            }
        }
        page.setRecords(resourceList);
        return page;
    }
}




