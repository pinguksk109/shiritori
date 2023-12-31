package jp.co.ot.shiritori.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ot.shiritori.domain.request.ShiritoriEntryRequest;
import jp.co.ot.shiritori.domain.request.ShiritoriWordRequest;
import jp.co.ot.shiritori.domain.request.ShiritoriWordRequestDto;
import jp.co.ot.shiritori.domain.response.EntryIdResponse;
import jp.co.ot.shiritori.domain.response.ShiritoriResultResponse;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShiritoriRepositoryTest {

	@Autowired
	private ShiritoriRepository sut;
	
	@Test
	@Disabled
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
	@Disabled
	public void judge_ワードを取得できること() {
		ShiritoriWordRequest request = new ShiritoriWordRequest();
		request.setWord("うんこ");
		try {
			ShiritoriResultResponse actual = sut.judge("hogehoge", new ShiritoriWordRequestDto(request));
			assertEquals("うんこ", actual.getWord());
		} catch (Exception e) {
			System.out.println("============================");
			System.out.println(e);
			System.out.println("============================");
		}
	}

	@Test
	@Disabled
	public void saveWord_キーワードを保存できること() {
		ShiritoriWordRequest request = new ShiritoriWordRequest();
		request.setWord("リンゴ");
		ShiritoriWordRequestDto requestDto = new ShiritoriWordRequestDto(request);
		try {
			sut.saveWord("testhoge30", requestDto);
		} catch (Exception e) {
			System.out.println("============================");
			System.out.println(e);
			System.out.println("============================");
		}
	}
	
	@Test
	@Disabled
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
	
	@Test
	@Disabled
	public void getLastKeyword_最後に登録したKeywordを取得できること() {
		try {
			ShiritoriResultResponse actual = sut.getLastKeyword("testhoge30");
			assertEquals("うんこ", actual.getWord());
		} catch (Exception e) {
			System.out.println("============================");
			System.out.println(e);
			System.out.println("============================");
		}
	}
	
	@Test
	@Disabled
	public void deleteWord_最後に登録したキーワードを取得できること() {
		try {
			sut.deleteWord("testhoge30");
		} catch (Exception e) {
			System.out.println("============================");
			System.out.println(e);
			System.out.println("============================");
		}
	}
	
	@Test
	@Disabled
	public void allGetKeyword_エントリーIDに紐づくすべてのキーワードを取得できること() {
		try {
			List<String> actual = sut.allGetKeyword("testhoge30");
			assertEquals(5, actual.size());
			assertEquals("リンゴ", actual.get(0));
			assertEquals("ごりら", actual.get(1));
			assertEquals("らっぱ", actual.get(2));
			assertEquals("パンツ", actual.get(3));
			assertEquals("つみき", actual.get(4));
		} catch(Exception e) {
			System.out.println("============================");
			System.out.println(e);
			System.out.println("============================");
		}
	}
	
	
	

}
