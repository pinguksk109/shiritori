package jp.co.ot.shiritori.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ot.shiritori.domain.request.ShiritoriEntryRequest;
import jp.co.ot.shiritori.repository.ShiritoriRepository;
import jp.co.ot.shiritori.response.ShiritoriEntryResponse;

@Service
public class ShiritoriService {

	@Autowired
	ShiritoriRepository shiritoriRepository;
	
	public ShiritoriEntryResponse entry(ShiritoriEntryRequest request) {
		
		shiritoriRepository.entry(request);
		
		return new ShiritoriEntryResponse(request.getEntryId());
		
	}
}
