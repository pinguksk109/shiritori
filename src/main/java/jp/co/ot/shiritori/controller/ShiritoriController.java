package jp.co.ot.shiritori.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jp.co.ot.shiritori.domain.exception.BadRequestException;
import jp.co.ot.shiritori.domain.exception.ConflictException;
import jp.co.ot.shiritori.domain.exception.UnprocessableEntityException;
import jp.co.ot.shiritori.domain.request.ShiritoriEntryRequest;
import jp.co.ot.shiritori.domain.request.ShiritoriWordRequest;
import jp.co.ot.shiritori.domain.response.ShiritoriResultResponse;
import jp.co.ot.shiritori.service.ShiritoriService;

@RestController
@RequestMapping("/v1/shiritori")
public class ShiritoriController {
	
	@Autowired
	private ShiritoriService shiritoriService;
	
	/**
	 * エントリーIDを登録する
	 * @param request
	 * @param bindingResult
	 * @return
	 * @throws BadRequestException
	 * @throws ConflictException
	 */
	@PostMapping("/entry")
	public ResponseEntity<?> entry(@RequestBody @Valid ShiritoriEntryRequest request, BindingResult bindingResult) throws BadRequestException, ConflictException {
	
		if(bindingResult.hasErrors()) {
			throw new BadRequestException("entryIdに不正がありました");
		}
		
		return ResponseEntity.ok().body(shiritoriService.entry(request));
		
	}
	
	/**
	 * 指定されたエントリーIDがあるかどうかを確認する
	 * @param entryId
	 * @param request
	 * @param bindingResult
	 * @return
	 * @throws BadRequestException
	 * @throws UnprocessableEntityException 
	 */
	@PostMapping("judge/entryId/{entryId}")
	public ResponseEntity<?> judge(@PathVariable("entryId") @NotBlank String entryId, @RequestBody @Valid ShiritoriWordRequest request, BindingResult bindingResult) throws BadRequestException, UnprocessableEntityException {
	
		if(bindingResult.hasErrors() || isKatakana(request.getWord())) {
			throw new BadRequestException("ワードに不正がありました");
		}
		
		ShiritoriResultResponse response = shiritoriService.judge(entryId, request);
		
		if(!Objects.isNull(response)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("入力された単語は既にありました");
		}
		
		// TODO: "入力された単語が「ん」だったら、エラー返したい"
		return ResponseEntity.ok().build();
	}
	
	/**
	 * 最後に登録されたキーワードを削除するエンドポイント
	 * @param entryId
	 * @return
	 * @throws BadRequestException
	 */
	@DeleteMapping("delete/entryId/{entryId}")
	public ResponseEntity<?> deleteWord(@PathVariable("entryId") String entryId) throws BadRequestException {
		
		ShiritoriResultResponse response = shiritoriService.deleteWord(entryId);
		
		if(Objects.isNull(response)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("登録されたキーワードがありせんでした");
		}
		
		return ResponseEntity.ok().body(response);
	}
	
	/**
	 * 指定されたエントリーIDに紐づくキーワードをすべて取得する
	 * @param entryId
	 * @return
	 * @throws BadRequestException
	 */
	@GetMapping("all/entryId/{entryId}")
	public ResponseEntity<?> allGetWord(@PathVariable("entryId") String entryId) throws BadRequestException {
		
		return ResponseEntity.ok(shiritoriService.allGetWord(entryId));
		
	}
	
	/**
	 * 受け取った引数が半角カタカナかどうかをbooleanで返す
	 * リクエストボディの@Patterで半角カタカナのバリデーションが通らないため自前で実装
	 * @param input
	 * @return
	 */
    private static boolean isKatakana(String input) {
        // 正規表現で半角カタカナのみを許可する条件をチェックする
        return input.matches("^[ｱ-ﾝﾞﾟ]+$");
    }

}
