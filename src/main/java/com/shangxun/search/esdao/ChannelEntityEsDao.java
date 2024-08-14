package com.shangxun.search.esdao;

import com.shangxun.search.model.dto.channelEntity.ChannelEntityEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * ChannelEntity ES 操作
 */
public interface ChannelEntityEsDao extends ElasticsearchRepository<ChannelEntityEsDTO, String> {
    List<ChannelEntityEsDTO> findByCodeName(String codeName);
}
