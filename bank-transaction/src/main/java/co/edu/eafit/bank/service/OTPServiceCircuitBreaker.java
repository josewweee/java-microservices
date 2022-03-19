package co.edu.eafit.bank.service;

import co.edu.eafit.bank.dto.OTPValidationResponse;

public interface OTPServiceCircuitBreaker {

	public OTPValidationResponse validateOTP(String user, String otp) throws Exception;
	
}

