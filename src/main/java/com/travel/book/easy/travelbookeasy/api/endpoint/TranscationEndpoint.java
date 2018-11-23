package com.travel.book.easy.travelbookeasy.api.endpoint;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travel.book.easy.travelbookeasy.services.interfaces.BraintreeService;

@RestController
@RequestMapping(value = "transcation", produces = "application/json")
public class TranscationEndpoint {
	
	@Autowired
	private BraintreeService braintreeService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/createTransaction")
	@Transactional
	public ResponseEntity createTrainRecord(
			@RequestParam("amount") String amount, @RequestParam("nonceFromTheClient") String nonceFromTheClient) {
		braintreeService.createTransaction(new BigDecimal(amount), nonceFromTheClient);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

}
