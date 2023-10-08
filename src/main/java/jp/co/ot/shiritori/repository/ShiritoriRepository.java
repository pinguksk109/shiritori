package jp.co.ot.shiritori.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ot.shiritori.domain.request.ShiritoriEntryRequest;
import jp.co.ot.shiritori.domain.request.ShiritoriWordRequestDto;
import jp.co.ot.shiritori.domain.response.EntryIdResponse;
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
	ShiritoriResultResponse judge(@Param("entryId") String entryId, @Param("request") ShiritoriWordRequestDto request);

	/**
	 * しりとりのキーワードを保存します
	 * @param entryId
	 * @param request
	 */
	void saveWord(@Param("entryId") String entryId, @Param("request") ShiritoriWordRequestDto request);

	/**
	 * エントリーIdを取得する
	 * @param entryId
	 * @return
	 */
	EntryIdResponse getEntryId(String entryId);
	
	/**
	 * 指定EntryIdの最後に登録されたキーワードを削除する
	 * @param entryId
	 */
	void deleteWord(@Param("entryId") String entryId);
	
	/**
	 * 指定EntryIdの最後に登録されたキーワードを取得する
	 * @param entryId
	 * @return
	 */
	ShiritoriResultResponse getLastKeyword(@Param("entryId") String entryId);
	
	

}