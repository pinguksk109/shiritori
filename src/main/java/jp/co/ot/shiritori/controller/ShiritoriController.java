package jp.co.ot.shiritori.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jp.co.ot.shiritori.domain.exception.BadRequestException;
import jp.co.ot.shiritori.domain.request.ShiritoriEntryRequest;
import jp.co.ot.shiritori.response.ShiritoriEntryResponse;
import jp.co.ot.shiritori.service.ShiritoriService;

@RestController
@RequestMapping("/v1/shiritori")
public class ShiritoriController {
	
	@Autowired
	private ShiritoriService shiritoriService;
	
	@PostMapping("/entry")
	public ResponseEntity<?> entry(@RequestBody @Valid ShiritoriEntryRequest request, BindingResult bindingResult) {
	
		if(bindingResult.hasErrors()) {
			throw new BadRequestException("entryIdに不正がありました");
		}
		
		ShiritoriEntryResponse response = shiritoriService.entry(request);
		
		return ResponseEntity.ok().body(response);
		
	}
	
//	@PostMapping("/post/entryId/{entryId}")
//	public ResponseEntity<?> judgeShiritori() {
//		
//	}
	

}
