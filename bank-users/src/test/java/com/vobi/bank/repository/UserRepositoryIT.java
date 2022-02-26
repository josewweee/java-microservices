package com.vobi.bank.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vobi.bank.domain.UserType;
import com.vobi.bank.domain.Users;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
class UserRepositoryIT {
	
	@Autowired
	UsersRepository userRepository;
	
	@Autowired
	UserTypeRepository userTypeRepository;

	@Test
	@Order(1)
	void debeValidarLasDependencias() {
		assertNotNull(userRepository);
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
		
		user=userRepository.save(user);
		
		//Assert
		
		assertNotNull(user,"El user es nulo no se pudo grabar");
	}
	
	@Test
	@Order(3)
	void debeModificarUnUser()throws Exception  {
		//Arrange
		
		String emailUser="jose3@gmail.edu";
		Users user=null;
		
		
		user=userRepository.findById(emailUser).get();
		user.setEnable("Y");
		
		//Act
		
		user=userRepository.save(user);
		
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
		
		assertTrue(userRepository.findById(emailUser).isPresent(),"No encontro el user");
		
		user=userRepository.findById(emailUser).get();
		
		//Act
		userRepository.delete(user);
		userOptional=userRepository.findById(emailUser);
		
		//Assert
		
		assertFalse(userOptional.isPresent(),"No pudo borrar el user");
	}
	
	@Test
	@Order(5)
	void debeConsultarTodosLosUsers()throws Exception  {
		//Arrange
		List<Users> users=null;
		
		//Act
		
		users=userRepository.findAll();
		
		users.forEach(user->log.info(user.getName()));		
		
		//Assert
		
		assertFalse(users.isEmpty(),"No consulto Users");	
	}
}