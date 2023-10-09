package jp.co.ot.shiritori.domain.response;

import java.util.List;

import lombok.Value;

@Value
public class ShiritoriAllKeywordResultResponseDto {

	private int wordCount;
	private List<String> word;
	
	public ShiritoriAllKeywordResultResponseDto(List<String> word) {
		this.wordCount = word.size();
		this.word = word;
	}
}
