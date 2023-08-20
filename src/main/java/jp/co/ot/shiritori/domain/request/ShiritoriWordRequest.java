package jp.co.ot.shiritori.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShiritoriWordRequest {

    @NotBlank(message = "wordが不正です")
	private String word;
	
}
