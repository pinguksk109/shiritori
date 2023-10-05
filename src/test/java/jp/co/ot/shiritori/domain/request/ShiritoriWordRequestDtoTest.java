package jp.co.ot.shiritori.domain.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ShiritoriWordRequestDtoTest {

	@Test
	void convertKatakanaToHiragana_カタカナを変換してひらがなを返すこと() {
		String actual = ShiritoriWordRequestDto.convertKatakanaToHiragana("リンゴ");
		assertEquals("りんご", actual);
	}
	
	@Test
	void convertKatakanaToHiragana_カタカナを変換してひらがなを返すこと_カタカナ全チェック() {
		String actual = ShiritoriWordRequestDto.convertKatakanaToHiragana("アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲン");
		assertEquals("あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもやゆよらりるれろわをん", actual);
	}

}
