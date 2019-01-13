package com.travel.book.easy.travelbookeasy.services.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.travel.book.easy.travelbookeasy.services.interfaces.BraintreeService;

@Service
public class BraintreeServiceImpl implements BraintreeService{

	@Value("${merchant_id}")
	private String merchant_id;
	
	@Value("${public_key}")
	private String public_key;
	
	@Value("${private_key}")
	private String private_key;

	@Override
	public boolean createTransaction(BigDecimal amount, String nonceFromTheClient) {
		
		BraintreeGateway gateway = new BraintreeGateway(
				  Environment.SANDBOX,
				  merchant_id,
				  public_key,
				  private_key
				);
		
		
		TransactionRequest request = new TransactionRequest()
				  .amount(amount)
				  .paymentMethodNonce(nonceFromTheClient)
				  .options()
				    .submitForSettlement(true)
				    .done();

		Result<Transaction> result = gateway.transaction().sale(request);

		return result.isSuccess();
	}

	@Override
	public String generateClientToken() {

		BraintreeGateway gateway = new BraintreeGateway(Environment.SANDBOX, merchant_id, public_key, private_key);

		String clientToken = gateway.clientToken().generate();

		return clientToken;
	}

}
