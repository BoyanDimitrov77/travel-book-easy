package com.travel.book.easy.travelbookeasy.services.interfaces;

import java.math.BigDecimal;

public interface BraintreeService {

	boolean createTransaction(BigDecimal amount, String nonceFromTheClient);

	String generateClientToken();
}
