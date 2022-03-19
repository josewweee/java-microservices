package co.edu.eafit.service;

import co.edu.eafit.dto.OTPValidationRequest;
import co.edu.eafit.dto.OTPValidationResponse;

public interface OTPService {

	public OTPValidationResponse validateOTP(OTPValidationRequest otpValidationRequest) throws Exception;
	
}
