package jp.co.ot.shiritori.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ShiritoriWordRequest {

    @NotBlank(message = "wordが不正です")
    @Pattern(regexp = "^[^\\s]+$", message = "キーワードには半角スペースを含めることはできません")
    @Pattern(regexp = "^[ァ-ヶｦ-ﾟ]*$", message = "半角カタカナを含めないでください")
    @Pattern(regexp = "^[^一-龯]*$", message = "漢字を含めないでください")
	private String word;
	
}
