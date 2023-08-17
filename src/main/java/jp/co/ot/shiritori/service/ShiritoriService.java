package jp.co.ot.shiritori.service;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ot.shiritori.service.request.ShiritoriEntryParam;

public class ShiritoriService {

	@Autowired
	ShiritoriRepository shiritoriRepository;
	
	public void entry(ShiritoriEntryParam param) {
		
		shiritoriRepository.entry(param);
		
	}

}
