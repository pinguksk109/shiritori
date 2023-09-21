package jp.co.ot.shiritori.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ot.shiritori.domain.exception.BadRequestException;
import jp.co.ot.shiritori.domain.request.ShiritoriEntryRequest;
import jp.co.ot.shiritori.domain.request.ShiritoriWordRequest;
import jp.co.ot.shiritori.domain.response.EntryIdResponse;
import jp.co.ot.shiritori.domain.response.ShiritoriEntryResponse;
import jp.co.ot.shiritori.domain.response.ShiritoriResultResponse;
import jp.co.ot.shiritori.repository.ShiritoriRepository;

@Service
public class ShiritoriService {

	@Autowired
	ShiritoriRepository shiritoriRepository;
	
	/**
	 * EntryIdの保存を行う
	 * @param request
	 * @return
	 */
	public ShiritoriEntryResponse entry(ShiritoriEntryRequest request) {
		
		// TODO: 既に出ているentryIdかもしれないからここで調べて分岐したい
		// 存在するentryIdだったら処理を進めない
		EntryIdResponse entryIdResponse = shiritoriRepository.getEntryId(request.getEntryId());
		
		if(!Objects.isNull(entryIdResponse)) {
			throw new BadRequestException(request.getEntryId());
		}
		
		shiritoriRepository.entry(request);
		
		return new ShiritoriEntryResponse(request.getEntryId());
		
	}

	/**
	 * 既に出ている単語かを調べる
	 * もしまだ出ていない単語であればその単語を保存する
	 * @param entryId
	 * @param request
	 * @return
	 */
	public ShiritoriResultResponse judge(String entryId, ShiritoriWordRequest request) {
		
		// 存在しないentryIdだったら処理を進めない
		EntryIdResponse entryIdResponse = shiritoriRepository.getEntryId(entryId);
		
		if(Objects.isNull(entryIdResponse)) {
			throw new BadRequestException(entryId);
		}
		
		ShiritoriResultResponse response = shiritoriRepository.judge(entryId, request);
		
		// 既にしりとりで出ているワードだったら、該当単語のレスポンスを返却する
		if(!Objects.isNull(response)) {
			return response;
		}
		
		shiritoriRepository.saveWord(entryId, request);
		
		return null;
		
	}
}
