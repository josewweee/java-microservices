package co.edu.eafit.bank.builder;

import co.edu.eafit.bank.domain.UserType;

public class UserTypeBuilder {

	private UserTypeBuilder() {
	}

	public static UserType getUserType() {
		UserType userType = new UserType();
		userType.setEnable("Y");
		userType.setName("CAJERO");
		userType.setUstyId(1);

		return userType;
	}

}
