package jp.co.ot.shiritori.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.ot.shiritori.domain.response.ShiritoriEntryResponse;
import jp.co.ot.shiritori.service.ShiritoriService;


class ShiritoriControllerTest extends ShiritoriController {

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

}
