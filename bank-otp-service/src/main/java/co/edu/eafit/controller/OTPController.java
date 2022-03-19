package co.edu.eafit.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.eafit.dto.OTPValidationRequest;
import co.edu.eafit.dto.OTPValidationResponse;
import co.edu.eafit.service.OTPService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/otp")
@Slf4j
public class OTPController {

	@Autowired
	OTPService otpService;
	
	@PostMapping("/validate")
	public OTPValidationResponse validateOTP(
			@Valid @RequestBody
			OTPValidationRequest otpValidationRequest) throws Exception {
		
		log.info("Validando el OTP " + otpValidationRequest.getOtp() + " del usuario " + otpValidationRequest.getUser());
		
		return otpService.validateOTP(otpValidationRequest);
	}
	
}
