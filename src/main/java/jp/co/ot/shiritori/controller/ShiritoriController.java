package jp.co.ot.shiritori.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ot.shiritori.service.ShiritoriService;

@RestController
@RequestMapping("/v1/shiritori")
public class ShiritoriController {
	
	@Autowired
	private ResponseHeader responseHeader();
	
	@Autowired
	private ShiritoriService shiritoriService;
	
	@PostMapping("/entry")
	public ResponseEntity<?> entry() {
	
		shiritoriService.entry();
		
	}
	
//	@PostMapping("/post")
//	public ResponseEntity<?> judgeShiritori() {
//		
//	}
	

}
