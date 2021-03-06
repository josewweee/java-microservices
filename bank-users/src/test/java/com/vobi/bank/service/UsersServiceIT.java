package com.vobi.bank.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vobi.bank.domain.UserType;
import com.vobi.bank.domain.Users;
import com.vobi.bank.repository.UserTypeRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
public class UsersServiceIT {
	@Autowired
	UsersService usersService;
	
	@Autowired
	UserTypeRepository userTypeRepository;

	@Test
	@Order(1)
	void debeValidarLasDependencias() {
		assertNotNull(usersService);
		assertNotNull(userTypeRepository);
	}
	
	@Test
	@Order(2)
	void debeCrearUnUser()throws Exception {
		//Arrange
		Integer idUserType=1;
		String userEmail="jose3@gmail.edu";
		
		Users user=null;
		UserType userType=userTypeRepository.findById(idUserType).get();
		
		user=new Users();
		user.setEnable("Y");
		user.setName("Name");
		user.setToken(null);
		user.setUserEmail(userEmail);
		user.setUserType(userType);

		//Act
		
		user=usersService.save(user);
		
		//Assert
		
		assertNotNull(user,"El user es nulo no se pudo grabar");
	}
	
	@Test
	@Order(3)
	void debeModificarUnUser()throws Exception  {
		//Arrange
		
		String emailUser="jose3@gmail.edu";
		Users user=null;
		
		
		user=usersService.findById(emailUser).get();
		user.setEnable("Y");
		
		//Act
		
		user=usersService.update(user);
		
		//Assert
		
		assertNotNull(user,"El user es nulo no se pudo modificar");
	}
	
	@Test
	@Order(4)
	void debeBorrarUnUser()throws Exception  {
		//Arrange
		
		String emailUser="jose3@gmail.edu";
		Users user=null;
		Optional<Users> userOptional=null;
		
		assertTrue(usersService.findById(emailUser).isPresent(),"No encontro el user");
		
		user=usersService.findById(emailUser).get();
		
		//Act
		usersService.delete(user);
		userOptional=usersService.findById(emailUser);
		
		//Assert
		
		assertFalse(userOptional.isPresent(),"No pudo borrar el user");
	}
	
	@Test
	@Order(5)
	void debeConsultarTodosLosUsers()throws Exception  {
		//Arrange
		List<Users> users=null;
		
		//Act
		
		users=usersService.findAll();
		
		users.forEach(user->log.info(user.getName()));		
		
		//Assert
		
		assertFalse(users.isEmpty(),"No consulto Users");	
	}
}
