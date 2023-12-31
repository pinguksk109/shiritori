package jp.co.ot.shiritori.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.co.ot.shiritori.domain.exception.BadRequestException;
import jp.co.ot.shiritori.domain.exception.ConflictException;
import jp.co.ot.shiritori.domain.exception.UnprocessableEntityException;
import jp.co.ot.shiritori.domain.request.ShiritoriEntryRequest;
import jp.co.ot.shiritori.domain.request.ShiritoriWordRequest;
import jp.co.ot.shiritori.domain.response.EntryIdResponse;
import jp.co.ot.shiritori.domain.response.ShiritoriAllKeywordResultResponseDto;
import jp.co.ot.shiritori.domain.response.ShiritoriEntryResponse;
import jp.co.ot.shiritori.domain.response.ShiritoriResultResponse;
import jp.co.ot.shiritori.repository.ShiritoriRepository;

@ExtendWith(MockitoExtension.class)
class ShiritoriServiceTest extends ShiritoriService {

	@InjectMocks
	private ShiritoriService sut;
	
	@Mock
	private ShiritoriRepository shiritoriRepository;
	
	@Test
	void entry_データを保存できること() throws ConflictException {
		
		ShiritoriEntryRequest request = new ShiritoriEntryRequest();
		request.setEntryId("hogehoge");
		
		doReturn(null).when(shiritoriRepository).getEntryId(anyString());
		doNothing().when(shiritoriRepository).entry(any());
		
		ShiritoriEntryResponse actual = sut.entry(request);
		assertEquals("hogehoge", actual.getEntryId());
		
	}
	
	@Test
	void entry_既に存在するentryの場合_ConflictExceptionをスローすること() { 
		
		ShiritoriEntryRequest request = new ShiritoriEntryRequest();
		request.setEntryId("hogehoge");
		
		EntryIdResponse response = new EntryIdResponse();
		response.setEntryId("hogehoge");
		
		doReturn(response).when(shiritoriRepository).getEntryId(anyString());
		
		assertThrows(ConflictException.class, () -> {
			sut.entry(request);
		});
	}
	
	@Test
	void entry_データ取得で何かしらの問題が発生した場合_RuntimeExceptionをスローすること() {
		
		ShiritoriEntryRequest request = new ShiritoriEntryRequest();
		request.setEntryId("hogehoge");
		
		doThrow(new RuntimeException()).when(shiritoriRepository).getEntryId(anyString());
		
		assertThrows(RuntimeException.class, () -> {
			sut.entry(request);
		});
	}
	
	@Test
	void judge_データを保存できること() throws BadRequestException, UnprocessableEntityException {
		
		ShiritoriWordRequest request = new ShiritoriWordRequest();
		request.setWord("りんご");
		
		EntryIdResponse response = new EntryIdResponse();
		response.setEntryId("hogehoge");
		
		doReturn(response).when(shiritoriRepository).getEntryId(anyString());
		doReturn(null).when(shiritoriRepository).judge(anyString(), any());
		doNothing().when(shiritoriRepository).saveWord(anyString(), any());
		
		ShiritoriResultResponse actual = sut.judge("hogehoge",request);
		assertNull(actual);
		
	}
	
	@Test
	void judge_存在しないEntryIDだった場合_BadRequesExceptionをスローすること() {
		
		ShiritoriWordRequest request = new ShiritoriWordRequest();
		request.setWord("りんご");
		
		EntryIdResponse response = null;
		
		doReturn(response).when(shiritoriRepository).getEntryId(anyString());
		
		assertThrows(BadRequestException.class, () -> {
			sut.judge("hogehoge",request);
		});
		
	}
	
	@Test
	void judge_既にしりとりで出ているキーワードの場合_そのキーワードを返すこと() throws BadRequestException, UnprocessableEntityException {
		
		ShiritoriWordRequest request = new ShiritoriWordRequest();
		request.setWord("りんご");
		
		EntryIdResponse idResponse = new EntryIdResponse();
		idResponse.setEntryId("hogehoge");
		
		ShiritoriResultResponse shiritoriResponse = new ShiritoriResultResponse("りんご", "リンゴ");
		
		doReturn(idResponse).when(shiritoriRepository).getEntryId(anyString());
		doReturn(shiritoriResponse).when(shiritoriRepository).judge(anyString(), any());
		
		ShiritoriResultResponse actual = sut.judge("hogehoge", request);
		
		assertEquals("りんご", actual.getWord());
		verify(shiritoriRepository, times(0)).saveWord(anyString(), any());
		
	}
	
	@Test
	void judge_んで終わる単語の場合_UnprocessableEntityExceptionをスローすること() {
		
		ShiritoriWordRequest request = new ShiritoriWordRequest();
		request.setWord("みかん");
		
		EntryIdResponse idResponse = new EntryIdResponse();
		idResponse.setEntryId("hogehoge");
		
		doReturn(idResponse).when(shiritoriRepository).getEntryId(anyString());
		
		assertThrows(UnprocessableEntityException.class, () -> {
			sut.judge("hogehoge", request);
		});
		
	}
	
	@Test
	void judge_何かしらの問題が発生した場合_RuntimeExceptionをスローすること() {
		
		ShiritoriWordRequest request = new ShiritoriWordRequest();
		request.setWord("りんご");
		
		doThrow(new RuntimeException()).when(shiritoriRepository).getEntryId(anyString());
		
		assertThrows(RuntimeException.class, () -> {
			sut.judge("hogehoge", request);
		});
		
	}
	
	@Test
	void deleteWord_キーワードの削除をした場合_削除したキーワードを返すこと() throws BadRequestException {
		
		EntryIdResponse idResponse = new EntryIdResponse();
		idResponse.setEntryId("hogehoge");
		doReturn(idResponse).when(shiritoriRepository).getEntryId(anyString());
		
		ShiritoriResultResponse response = new ShiritoriResultResponse("りんご", "リンゴ");
		doReturn(response).when(shiritoriRepository).getLastKeyword(anyString());
		
		doNothing().when(shiritoriRepository).deleteWord(anyString());
		
		ShiritoriResultResponse actual = sut.deleteWord("hogehoge");
		assertEquals("りんご", actual.getWord());
		
	}
	
	@Test
	void deleteWord_登録されたキーワードがない場合_Nullが返却されること() throws BadRequestException {
		
		EntryIdResponse idResponse = new EntryIdResponse();
		idResponse.setEntryId("hogehoge");
		doReturn(idResponse).when(shiritoriRepository).getEntryId(anyString());
		
		doReturn(null).when(shiritoriRepository).getLastKeyword(anyString());
		
		ShiritoriResultResponse actual = sut.deleteWord("hogehoge");
		assertNull(actual);
		
	}
	
	@Test
	void deleteWord_存在しないEntryIdが指定された場合_BadRequestExceptionがスローされること() throws BadRequestException {

		doReturn(null).when(shiritoriRepository).getEntryId(anyString());
		
		assertThrows(BadRequestException.class, () -> {
			sut.deleteWord("hogehoge");
		});
		
	}
	
	@Test
	void deleteWord_何かしらの問題が発生した場合_RuntimeExceptionがスローされること() throws BadRequestException {

		doThrow(new RuntimeException()).when(shiritoriRepository).getEntryId(anyString());
		
		assertThrows(RuntimeException.class, () -> {
			sut.deleteWord("hogehoge");
		});
		
	}
	
	@Test
	void allGetWord_処理が正常に行われた場合_すべてのキーワードを返すこと() throws BadRequestException {
		
		List<String> word = new ArrayList<>();
		word.add("りんご");
		word.add("ゴリラ");
		word.add("ラッパ");
		
		EntryIdResponse idResponse = new EntryIdResponse();
		idResponse.setEntryId("testhoge30");
		
		doReturn(idResponse).when(shiritoriRepository).getEntryId(anyString());
		doReturn(word).when(shiritoriRepository).allGetKeyword(anyString());
		
		ShiritoriAllKeywordResultResponseDto actual = sut.allGetWord("testhoge30");
		
		assertEquals(3, actual.getWord().size());
		assertEquals(3, actual.getWordCount());
		assertEquals("りんご", actual.getWord().get(0));
		assertEquals("ゴリラ", actual.getWord().get(1));
		assertEquals("ラッパ", actual.getWord().get(2));
		
	}
	
	@Test
	void allGetWord_存在しないEntryIdが指定された場合_BadRequestExceptionをスローすること() throws BadRequestException {
		
		doReturn(null).when(shiritoriRepository).getEntryId(anyString());
		
		assertThrows(BadRequestException.class, () -> {
			sut.allGetWord("testhoge30");
		});		
	}
	
	@Test
	void allGetWord_エントリーIDに紐づくキーワードがない場合_Nullを返却すること() throws BadRequestException {
		
		EntryIdResponse idResponse = new EntryIdResponse();
		idResponse.setEntryId("testhoge30");
		
		doReturn(idResponse).when(shiritoriRepository).getEntryId(anyString());
		doReturn(null).when(shiritoriRepository).allGetKeyword(anyString());

		
		ShiritoriAllKeywordResultResponseDto actual = sut.allGetWord("testhoge30");
		
		assertNull(actual);
	}
	
	@Test
	void allGetWord_何かしらの問題が発生した場合_RuntimeExceptionがスローされること() throws BadRequestException {
		
		EntryIdResponse idResponse = new EntryIdResponse();
		idResponse.setEntryId("testhoge30");
		
		doReturn(idResponse).when(shiritoriRepository).getEntryId(anyString());
		doThrow(new RuntimeException()).when(shiritoriRepository).allGetKeyword(anyString());

		assertThrows(RuntimeException.class, () -> {
			sut.allGetWord("testhoge30");
		});
		
	}



}
