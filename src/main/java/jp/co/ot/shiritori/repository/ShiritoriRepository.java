package jp.co.ot.shiritori.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ot.shiritori.domain.request.ShiritoriEntryRequest;

@Repository
@Mapper
public interface ShiritoriRepository {

	void entry(@Param("request")ShiritoriEntryRequest request);

}