package jp.co.ot.shiritori.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ot.shiritori.domain.request.ShiritoriEntryRequest;
import jp.co.ot.shiritori.domain.request.ShiritoriWordRequest;
import jp.co.ot.shiritori.domain.response.EntryIdResponse;
import jp.co.ot.shiritori.domain.response.ShiritoriResultResponse;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShiritoriRepositoryTest {

	@Autowired
	private ShiritoriRepository sut;
	
	@Test
	@Ignore
	public void entry_entryIdを保存できること() {
		ShiritoriEntryRequest request = new ShiritoriEntryRequest();
		request.setEntryId("testhoge30");
		try {
			sut.entry(request);
		} catch (Exception e) {
			System.out.println("============================");
			System.out.println(e);
			System.out.println("============================");
		}
	}
	
	@Test
	@Ignore
	public void judge_ワードを取得できること() {
		ShiritoriWordRequest request = new ShiritoriWordRequest();
		request.setWord("うんこ");
		try {
			ShiritoriResultResponse actual = sut.judge("hogehoge", request);
			assertEquals("うんこ", actual.getWord());
		} catch (Exception e) {
			System.out.println("============================");
			System.out.println(e);
			System.out.println("============================");
		}
	}

	@Test
	@Ignore
	public void saveWord_キーワードを保存できること() {
		ShiritoriWordRequest request = new ShiritoriWordRequest();
		request.setWord("うんこ");
		try {
			sut.saveWord("testhoge30", request);
		} catch (Exception e) {
			System.out.println("============================");
			System.out.println(e);
			System.out.println("============================");
		}
	}
	
	@Test
//	@Ignore
	public void getEntryId_EntryIdを取得できること() {
		try {
			EntryIdResponse actual = sut.getEntryId("testhhogehoge30");
			assertEquals("testhhogehoge30", actual.getEntryId());
		} catch (Exception e) {
			System.out.println("============================");
			System.out.println(e);
			System.out.println("============================");
		}
	}
	
	
	

}
