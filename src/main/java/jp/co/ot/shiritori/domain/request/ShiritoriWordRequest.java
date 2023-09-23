package jp.co.ot.shiritori.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ShiritoriWordRequest {

    @NotBlank(message = "wordが不正です")
    @Pattern(regexp = "^[^\\s]+$", message = "キーワードには半角スペースを含めることはできません")
	private String word;
	
}
