package com.vobi.bank.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vobi.bank.domain.Users;
import com.vobi.bank.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {
	
	@Autowired
	UsersRepository userRepository;
	
	@Autowired
	Validator validator;
	
	@Override
	@Transactional(readOnly = true)
	public List<Users> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Users> findById(String id) {
		return userRepository.findById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return userRepository.count();
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public Users save(Users entity) throws Exception {
		if(entity==null) {
			throw new Exception("El user es nulo");
		}
		
		validate(entity);
		
		if(userRepository.existsById(entity.getUserEmail())) {
			throw new Exception("El user ya existe");
		}
		
		return userRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public Users update(Users entity) throws Exception {
		if(entity==null) {
			throw new Exception("El user es nulo");
		}
		
		validate(entity);
		
		if(userRepository.existsById(entity.getUserEmail())==false) {
			throw new Exception("El user no existe");
		}
		
		return userRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(Users entity) throws Exception {
		if(entity==null) {
			throw new Exception("El user es nulo");
		}
		
		if(entity.getUserEmail()==null) {
			throw new Exception("El user id es nulo");
		}
		
		if(userRepository.existsById(entity.getUserEmail())==false) {
			throw new Exception("El user no existe");
		}
		
		userRepository.delete(entity);
		
		
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		if(id==null) {
			throw new Exception("El id es nulo");
		}
		
		if(userRepository.existsById(id)==false) {
			throw new Exception("El user no existe");
		}
		
		delete(userRepository.findById(id).get());	
	}

	@Override
	public void validate(Users entity) throws Exception {
		Set<ConstraintViolation<Users>> constraintViolations=validator.validate(entity);
		if(constraintViolations.isEmpty()==false) {
			throw new ConstraintViolationException(constraintViolations);
		}		
		
	}

}
