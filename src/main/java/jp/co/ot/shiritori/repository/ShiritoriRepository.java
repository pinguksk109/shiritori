package jp.co.ot.shiritori.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ot.shiritori.domain.request.ShiritoriEntryRequest;
import jp.co.ot.shiritori.domain.request.ShiritoriWordRequest;
import jp.co.ot.shiritori.domain.response.ShiritoriResultResponse;

@Repository
@Mapper
public interface ShiritoriRepository {

	/**
	 * DBにEntryIdを保存する
	 * @param request
	 */
	void entry(@Param("request") ShiritoriEntryRequest request);
	
	/**
	 * 受け取ったワードをDBから抽出して返却する
	 * @param entryId
	 * @param request
	 * @return
	 */
	ShiritoriResultResponse judge(@Param("entryId") String entryId, @Param("request") ShiritoriWordRequest request);

	void saveWord(@Param("entryId") String entryId, @Param("request") ShiritoriWordRequest request);

}