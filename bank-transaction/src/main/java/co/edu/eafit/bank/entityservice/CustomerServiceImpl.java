package co.edu.eafit.bank.entityservice;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.eafit.bank.domain.Account;
import co.edu.eafit.bank.domain.Customer;
import co.edu.eafit.bank.domain.RegisteredAccount;

import co.edu.eafit.bank.repository.CustomerRepository;


import lombok.extern.slf4j.Slf4j;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 * 
 */

@Scope("singleton")
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private Validator validator;

	@Override
	public void validate(Customer customer) throws ConstraintViolationException {

		Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(constraintViolations);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return customerRepository.count();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> findAll() {
		log.debug("finding all Customer instances");
		return customerRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Customer save(Customer entity) throws Exception {
		log.debug("saving Customer instance");

		if (entity == null) {
			throw new Exception("The customer is null");
		}

		validate(entity);

		if (customerRepository.existsById(entity.getCustId())) {
			throw new Exception("The customer already exists");
		}

		return customerRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Customer entity) throws Exception {
		log.debug("deleting Customer instance");

		if (entity == null) {
			throw new Exception("The customer is null");
		}

		if (entity.getCustId() == null) {
			throw new Exception("The Customer Id is null");
		}

		if (customerRepository.existsById(entity.getCustId()) == false) {
			throw new Exception("The Customer no found");
		}

		findById(entity.getCustId()).ifPresent(entidad -> {
			List<Account> accounts = entidad.getAccounts();
			if (accounts!=null && accounts.isEmpty()==false) {
				throw new RuntimeException("The Customer has accounts");
			}
			List<RegisteredAccount> registeredAccounts = entidad.getRegisteredAccounts();
			if (registeredAccounts!=null && registeredAccounts.isEmpty()==false) {
				throw new RuntimeException("The Customer has Registered Accounts");
			}
		});

		customerRepository.deleteById(entity.getCustId());
		log.debug("delete Customer successful");

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {
		log.debug("deleting Customer instance");
		if (id == null) {
			throw new Exception("The id is null");
		}
		if (customerRepository.existsById(id)) {
			delete(customerRepository.findById(id).get());
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Customer update(Customer entity) throws Exception {

		log.debug("updating Customer instance");

		if (entity == null) {
			throw new Exception("The customer is null");
		}

		if (entity.getCustId() == null) {
			throw new Exception("The Customer Id is null");
		}

		if (customerRepository.existsById(entity.getCustId()) == false) {
			throw new Exception("The Customer no found");
		}

		validate(entity);

		return customerRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Customer> findById(Integer custId) {
		log.debug("getting Customer instance");
		return customerRepository.findById(custId);
	}

}
