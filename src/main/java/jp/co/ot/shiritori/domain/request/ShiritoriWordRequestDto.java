package jp.co.ot.shiritori.domain.request;

import java.text.Normalizer;

import lombok.Value;

@Value
public class ShiritoriWordRequestDto {

	private String rawWord;
	private String word;
	
	public ShiritoriWordRequestDto(ShiritoriWordRequest request) {
		this.rawWord = request.getWord();
		this.word = convertKatakanaToHiragana(request.getWord());	}
	
    public static String convertKatakanaToHiragana(String katakana) {
        // カタカナをひらがなに変換
        String normalizedString = Normalizer.normalize(katakana, Normalizer.Form.NFKC);
        StringBuilder hiragana = new StringBuilder();

        for (char c : normalizedString.toCharArray()) {
            // カタカナの文字コード範囲: U+30A0 ～ U+30FF
            if (c >= '\u30A0' && c <= '\u30FF') {
                // カタカナをひらがなに変換
                hiragana.append((char) (c - '\u30A0' + '\u3040'));
            } else {
                hiragana.append(c);
            }
        }

        return hiragana.toString();
    }
	
}
