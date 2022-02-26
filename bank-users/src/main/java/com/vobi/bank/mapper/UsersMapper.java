package com.vobi.bank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vobi.bank.domain.Users;
import com.vobi.bank.dto.UsersDTO;

import java.util.List;

@Mapper
public interface UsersMapper {

	@Mapping(source = "userType.ustyId" , target ="usty_id" )
	public UsersDTO usersToUserDTO(Users user);
	
	@Mapping(target = "userType.ustyId" , source ="usty_id" )
	public Users usersDTOtoUsers(UsersDTO userDTO);
	
	public List<UsersDTO> usersListToUsersDTOList(List<Users> users);
	
	public List<Users> usersDTOListToUsersList(List<Users> usersDTOs);
}
