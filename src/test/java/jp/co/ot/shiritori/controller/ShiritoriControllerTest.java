package jp.co.ot.shiritori.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.ot.shiritori.domain.exception.BadRequestException;
import jp.co.ot.shiritori.domain.exception.ConflictException;
import jp.co.ot.shiritori.domain.exception.UnprocessableEntityException;
import jp.co.ot.shiritori.domain.response.ShiritoriAllKeywordResultResponseDto;
import jp.co.ot.shiritori.domain.response.ShiritoriEntryResponse;
import jp.co.ot.shiritori.domain.response.ShiritoriResultResponse;
import jp.co.ot.shiritori.service.ShiritoriService;


class ShiritoriControllerTest extends ShiritoriController {

    @Autowired
	private MockMvc mvc;
	
	@InjectMocks
	private ShiritoriController sut;
	
	@Mock
	private ShiritoriService shiritoriService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
    	mvc = MockMvcBuilders.standaloneSetup(sut).build();;
	}
	
	@Test
	void POST_entry_処理が正常に行われた場合_HTTPステータス200を返すこと() throws Exception {
		
		ShiritoriEntryResponse response = new ShiritoriEntryResponse("hogehoge");		
		doReturn(response).when(shiritoriService).entry(any());

		String requestBody = "{\"entryId\": \"hogehogehoge\"}";
		
        mvc.perform(MockMvcRequestBuilders.post("/v1/shiritori/entry")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
	}
	
	@Test
	void POST_entry_ConflictExceptionがスローされた場合_HTTPステータス400を返すこと() throws Exception {	
		doThrow(new ConflictException(null)).when(shiritoriService).entry(any());
		
		String requestBody = "{\"entryId\": \"hogehogehogehoge\"}";
	
		mvc.perform(MockMvcRequestBuilders.post("/v1/shiritori/entry")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isConflict());
	}
	
	@Test
	void POST_judge_処理が正常に行われた場合_HTTPステータス200を返すこと() throws Exception {
		doReturn(null).when(shiritoriService).judge(anyString(), any());
		String requestBody = "{\"word\": \"りんご\"}";
		
        mvc.perform(MockMvcRequestBuilders.post("/v1/shiritori/judge/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
	}
	
	@Test
	void POST_judge_キーワードが空文字の場合_HTTPステータス400を返すこと() throws Exception {
		String requestBody = "{\"word\": \"\"}";
		
        mvc.perform(MockMvcRequestBuilders.post("/v1/shiritori/judge/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
	}
	
	@Test
	void POST_judge_キーワードに半角スペースが含まれる場合_HTTPステータス400を返すこと() throws Exception {
		String requestBody = "{\"word\": \"りん ご\"}";
		
        mvc.perform(MockMvcRequestBuilders.post("/v1/shiritori/judge/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
	}
	
	@Test
	void POST_judge_キーワードに半角カタカナが含まれる場合_HTTPステータス400を返すこと() throws Exception {
		String requestBody = "{\"word\": \"ﾘﾝｺﾞ\"}";
		
        mvc.perform(MockMvcRequestBuilders.post("/v1/shiritori/judge/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
	}
	
	@Test
	void POST_judge_キーワードに漢字が含まれる場合_HTTPステータス400を返すこと() throws Exception {
		String requestBody = "{\"word\": \"林檎ﾞ\"}";
		
        mvc.perform(MockMvcRequestBuilders.post("/v1/shiritori/judge/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
	}
	
	@Test
	void POST_judge_キーワードがNullの場合_HTTPステータス400を返すこと() throws Exception {
		String requestBody = "";
		
        mvc.perform(MockMvcRequestBuilders.post("/v1/shiritori/judge/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
	}
	
	@Test
	void POST_judge_BadRequestExceptionがスローされた場合_HTTPステータス400を返すこと() throws Exception {
		doThrow(new BadRequestException(null)).when(shiritoriService).judge(anyString(), any());
		String requestBody = "{\"word\": \"りんご\"}";
		
        mvc.perform(MockMvcRequestBuilders.post("/v1/shiritori/judge/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
	}
	
	@Test
	void POST_judge_UnprocessableEntityExceptionがスローされた場合_HTTPステータス400を返すこと() throws Exception {
		
		doThrow(new UnprocessableEntityException(null)).when(shiritoriService).judge(anyString(), any());
		String requestBody = "{\"word\": \"みかん\"}";
		
        mvc.perform(MockMvcRequestBuilders.post("/v1/shiritori/judge/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andReturn();
	}
	
	@Test
	void DELETE_deleteWord_処理が正常に行われた場合_HTTPステータス200を返すこと() throws Exception {
		doReturn(new ShiritoriResultResponse("りんご", "リンゴ")).when(shiritoriService).deleteWord(anyString());
		
        mvc.perform(MockMvcRequestBuilders.delete("/v1/shiritori/delete/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void DELETE_deleteWord_登録されたキーワードがない場合_HTTPステータス204を返すこと() throws Exception {
		doReturn(null).when(shiritoriService).deleteWord(anyString());
		
        mvc.perform(MockMvcRequestBuilders.delete("/v1/shiritori/delete/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	void DELETE_deleteWord_BadRequestExceptionがスローされた場合_HTTPステータス400を返すこと() throws Exception {
		doThrow(new BadRequestException(null)).when(shiritoriService).deleteWord(anyString());
		
        mvc.perform(MockMvcRequestBuilders.delete("/v1/shiritori/delete/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void GET_allGetWord_処理が正常に行われた場合_HTTPステータス200を返すこと() throws Exception {
		
        List<String> word = new ArrayList<>();
        word.add("リンゴ");
        word.add("ゴリラ");
        word.add("らっぱ");
		ShiritoriAllKeywordResultResponseDto dto = new ShiritoriAllKeywordResultResponseDto(word);
		
		doReturn(dto).when(shiritoriService).allGetWord(anyString());
		
        mvc.perform(get("/v1/shiritori/all/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
	}
	
	@Test
	void GET_allGetWord_処理が正常に行われた場合_キーワードを返すこと() throws Exception {
		
        List<String> word = new ArrayList<>();
        word.add("リンゴ");
        word.add("ゴリラ");
        word.add("らっぱ");
		ShiritoriAllKeywordResultResponseDto dto = new ShiritoriAllKeywordResultResponseDto(word);
		
		doReturn(dto).when(shiritoriService).allGetWord(anyString());
		
        MvcResult actual = mvc.perform(get("/v1/shiritori/all/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        
        String expected = "{\"wordCount\":3,\"word\":[\"リンゴ\",\"ゴリラ\",\"らっぱ\"]}";
        
        assertEquals(expected, actual.getResponse().getContentAsString(StandardCharsets.UTF_8));
	}
	
	@Test
	void GET_allGetWord_処理が正常に行われServiceからNullが返却された場合_HTTPステータスコードのみ返すこと() throws Exception {
		
		doReturn(null).when(shiritoriService).allGetWord(anyString());
		
		MvcResult actual = mvc.perform(get("/v1/shiritori/all/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
		
		String expected = "";
		
		assertEquals(expected, actual.getResponse().getContentAsString());
	}
	
	@Test
	void GET_allGetWord_BadRequestExceptionがスローされた場合_HTTPステータスコード400を返すこと() throws Exception {
		
		doThrow(new BadRequestException(null)).when(shiritoriService).allGetWord(anyString());
		
		mvc.perform(get("/v1/shiritori/all/entryId/hogehoge")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
	}
	
}
