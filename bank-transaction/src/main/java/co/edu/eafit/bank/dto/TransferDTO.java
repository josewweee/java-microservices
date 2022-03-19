package co.edu.eafit.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TransferDTO {

	private String accoIdOrigin;
	private String accoIdDestination;
	private Double amount;
	private String userEmail;
	private String token;

}
