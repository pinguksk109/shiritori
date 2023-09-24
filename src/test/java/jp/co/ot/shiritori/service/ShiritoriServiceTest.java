package jp.co.ot.shiritori.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.co.ot.shiritori.domain.exception.BadRequestException;
import jp.co.ot.shiritori.domain.exception.ConflictException;
import jp.co.ot.shiritori.domain.request.ShiritoriEntryRequest;
import jp.co.ot.shiritori.domain.request.ShiritoriWordRequest;
import jp.co.ot.shiritori.domain.response.EntryIdResponse;
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
	void judge_データを保存できること() throws BadRequestException {
		
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
	void judge_既にしりとりで出ているキーワードの場合_そのキーワードを返すこと() throws BadRequestException {
		
		ShiritoriWordRequest request = new ShiritoriWordRequest();
		request.setWord("りんご");
		
		EntryIdResponse idResponse = new EntryIdResponse();
		idResponse.setEntryId("hogehoge");
		
		ShiritoriResultResponse shiritoriResponse = new ShiritoriResultResponse("りんご");
		
		doReturn(idResponse).when(shiritoriRepository).getEntryId(anyString());
		doReturn(shiritoriResponse).when(shiritoriRepository).judge(anyString(), any());
		
		ShiritoriResultResponse actual = sut.judge("hogehoge", request);
		
		assertEquals("りんご", actual.getWord());
		verify(shiritoriRepository, times(0)).saveWord(anyString(), any());
		
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

}
