package co.edu.eafit.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTPValidationResponse {

	private Boolean valid;
	private Integer doubleCheckCode;
	String errorMessage;
	
}
