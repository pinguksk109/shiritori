package jp.co.ot.shiritori.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jp.co.ot.shiritori.domain.exception.BadRequestException;
import jp.co.ot.shiritori.domain.exception.ConflictException;
import jp.co.ot.shiritori.domain.request.ShiritoriEntryRequest;
import jp.co.ot.shiritori.domain.request.ShiritoriWordRequest;
import jp.co.ot.shiritori.domain.response.ShiritoriResultResponse;
import jp.co.ot.shiritori.service.ShiritoriService;

@RestController
@RequestMapping("/v1/shiritori")
public class ShiritoriController {
	
	@Autowired
	private ShiritoriService shiritoriService;
	
	@PostMapping("/entry")
	public ResponseEntity<?> entry(@RequestBody @Valid ShiritoriEntryRequest request, BindingResult bindingResult) throws BadRequestException, ConflictException {
	
		if(bindingResult.hasErrors()) {
			throw new BadRequestException("entryIdに不正がありました");
		}
		
		return ResponseEntity.ok().body(shiritoriService.entry(request));
		
	}
	
	@PostMapping("judge/entryId/{entryId}")
	public ResponseEntity<?> judge(@PathVariable("entryId") @NotBlank String entryId, @RequestBody @Valid ShiritoriWordRequest request, BindingResult bindingResult) throws BadRequestException {
	
		if(bindingResult.hasErrors()) {
			throw new BadRequestException("ワードに不正がありました");
		}
		
		ShiritoriResultResponse response = shiritoriService.judge(entryId, request);
		
		if(!Objects.isNull(response)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("入力された単語は既にありました");
		}
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("delete/entryId/{entryId}")
	public ResponseEntity<?> deleteWord(@PathVariable("entryId") String entryId, BindingResult bindingResult) throws BadRequestException {
	
		
		
		return null;
	}
	

}
