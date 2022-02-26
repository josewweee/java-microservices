package com.vobi.bank.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vobi.bank.domain.Users;
import com.vobi.bank.dto.UsersDTO;
import com.vobi.bank.mapper.UsersMapper;
import com.vobi.bank.service.UsersService;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
	
	@Autowired
	UsersService userService;
	
	@Autowired
	UsersMapper usersMapper;
	
	@PostMapping
	public UsersDTO save(@Valid @RequestBody UsersDTO usersDTO)throws Exception{
		Users user=usersMapper.usersDTOtoUsers(usersDTO);
		user=userService.save(user);
		usersDTO=usersMapper.usersToUserDTO(user);
		
		return usersDTO;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String id) throws Exception{		
		userService.deleteById(id);
	}
	
	@PutMapping
	public UsersDTO update(@Valid @RequestBody UsersDTO usersDTO)throws Exception{
		Users user=usersMapper.usersDTOtoUsers(usersDTO);
		user=userService.update(user);
		usersDTO=usersMapper.usersToUserDTO(user);
		
		return usersDTO;
	}
	

	@GetMapping("/{id}")
	public UsersDTO findById(@PathVariable("id") String id) throws Exception{		
		Users user=null;
		UsersDTO usersDTO=null;
		if(userService.findById(id).isPresent()==true)
			user=userService.findById(id).get();
		
		usersDTO=usersMapper.usersToUserDTO(user);
		
		return usersDTO;
	}
	
	@GetMapping()
	public List<UsersDTO> findAll()throws Exception{
		
		List<Users> users=userService.findAll();
		List<UsersDTO> usersDTO=usersMapper.usersListToUsersDTOList(users);
		
		return usersDTO;
	}

}