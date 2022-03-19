package co.edu.eafit.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.eafit.bank.dto.OTPValidationRequest;
import co.edu.eafit.bank.dto.OTPValidationResponse;
import co.edu.eafit.bank.openfeignclients.FeignClients;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Component
public class OTPServiceCircuitBreakerImpl implements OTPServiceCircuitBreaker {

	@Autowired
	FeignClients feignClients;
	
	@Override
	@CircuitBreaker(
			name = "bank-otp",
			fallbackMethod = "fallbackValidateOTP"
			)
	public OTPValidationResponse validateOTP(String user, String otp) throws Exception {
		// Using OpenFeign
		OTPValidationRequest otpValidationRequest = new OTPValidationRequest(user, otp);
		return feignClients.validateOTP(otpValidationRequest);
	}
	
	public OTPValidationResponse fallbackValidateOTP(String user, String otp, Throwable th) throws Exception {
		throw new Exception("OTPService is not available");
	}

}

