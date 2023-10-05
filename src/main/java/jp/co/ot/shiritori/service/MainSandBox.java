package jp.co.ot.shiritori.service;

import java.text.Normalizer;

public class MainSandBox {

	public static void main(String[] args) {
		System.out.println(convertKatakanaToHiragana("リンゴをたべたいなケイスケハ"));
	}
    
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

