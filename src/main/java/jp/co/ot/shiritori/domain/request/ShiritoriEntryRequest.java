package jp.co.ot.shiritori.domain.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShiritoriEntryRequest{

    @Size(max = 30, message = "entryIdは30文字以下で入力してください")
    @NotBlank(message = "entryIdが空です")
	private String entryId;
	
}
