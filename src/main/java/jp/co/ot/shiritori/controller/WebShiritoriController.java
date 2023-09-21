package jp.co.ot.shiritori.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jp.co.ot.shiritori.domain.exception.BadRequestException;
import jp.co.ot.shiritori.domain.request.ShiritoriEntryRequest;
import jp.co.ot.shiritori.domain.request.ShiritoriWordRequest;
import jp.co.ot.shiritori.domain.response.ShiritoriResultResponse;
import jp.co.ot.shiritori.service.ShiritoriService;

@Controller
@RequestMapping("/v1/shiritori")
public class WebShiritoriController {
	
	@Autowired
	private ShiritoriService shiritoriService;
	
	@PostMapping("/entry/web")
	public ModelAndView entry(@RequestBody @Valid ShiritoriEntryRequest request, BindingResult bindingResult) {
	
		ModelAndView modelAndView = new ModelAndView();
		
		if(bindingResult.hasErrors()) {
			throw new BadRequestException("entryIdに不正がありました");
		}
		
		modelAndView.addObject("result", shiritoriService.entry(request));
		modelAndView.setViewName("entryResult");
		
		
		return modelAndView;
		
	}
	
	@PostMapping("judge/entryId/{entryId}/web")
	public ModelAndView judge(@PathVariable("entryId") @NotBlank String entryId, @RequestBody @Valid ShiritoriWordRequest request, BindingResult bindingResult) {
	
		ModelAndView modelAndView = new ModelAndView();
	
		
		if(bindingResult.hasErrors()) {
			throw new BadRequestException("ワードに不正がありました");
		}
		
		ShiritoriResultResponse response = shiritoriService.judge(entryId, request);
		
        if (!Objects.isNull(response)) {
            modelAndView.addObject("error", "入力された単語は既にありました");
            modelAndView.setViewName("errorPage");  // Thymeleafのエラーページ名
        } else {
            modelAndView.setViewName("successPage");  // 成功時のThymeleafのビュー名
        }
		
		return modelAndView;
	}
	
	@GetMapping("/index")
	public String showIndex() {
		return "index";
	}
	

}
