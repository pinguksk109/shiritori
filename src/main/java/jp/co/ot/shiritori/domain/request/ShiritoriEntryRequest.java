package jp.co.ot.shiritori.domain.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ShiritoriEntryRequest{

	@NotBlank
	private String entryId;
	
}
