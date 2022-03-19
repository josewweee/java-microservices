package co.edu.eafit.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.edu.eafit.domain.Users;
import co.edu.eafit.dto.OTPValidationRequest;
import co.edu.eafit.dto.OTPValidationResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OTPServiceImpl implements OTPService {
	
	@Autowired
	private UsersService usersService;

	@Override
	@Transactional(readOnly = true)
	public OTPValidationResponse validateOTP(OTPValidationRequest otpValidationRequest) throws Exception {

		log.info("Validando el OTP " + otpValidationRequest.getOtp() + 
				" del usuario " + otpValidationRequest.getUser());
		
		//Se consulta el usuario
		String userEmail = otpValidationRequest.getUser();
		Optional<Users> userOptional = usersService.findById(userEmail);
		
		if (!userOptional.isPresent()) {
			throw new Exception ("No existe el usaurio " + userEmail);
		}
		
		//Se obtiene el token del usuario
		Users user = userOptional.get();
		String token = user.getToken();
		String tokenToValidate = otpValidationRequest.getOtp();
		
		OTPValidationResponse otpValidationResponse = new OTPValidationResponse();
		
		//Se valida si el token coincide
		if (token!=null && token.equals(tokenToValidate)) {
			Random rnd = new Random();
			int doubleCheckNumber = rnd.nextInt(9999-1111) + 1111;
			otpValidationResponse.setDoubleCheckCode(doubleCheckNumber);
			
			otpValidationResponse.setValid(true);
			otpValidationResponse.setDoubleCheckCode(doubleCheckNumber);
		} else {
			
			//El token no coincide
			otpValidationResponse.setValid(false);
			otpValidationResponse.setErrorMessage("Invalid token");
			
		}
		
		return otpValidationResponse;
	}

}
