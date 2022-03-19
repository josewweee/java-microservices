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
import co.edu.eafit.bank.domain.RegisteredAccount;
import co.edu.eafit.bank.domain.Transaction;

import co.edu.eafit.bank.repository.AccountRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 * 
 */

@Scope("singleton")
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private Validator validator;

	@Override
	public void validate(Account account) throws ConstraintViolationException {

		Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(constraintViolations);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return accountRepository.count();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Account> findAll() {
		log.debug("finding all Account instances");
		return accountRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Account save(Account entity) throws Exception {
		log.debug("saving Account instance");

		if (entity == null) {
			throw new RuntimeException("The Account is null");
		}

		validate(entity);

		if (accountRepository.existsById(entity.getAccoId())) {
			throw new RuntimeException("The Account already exists");
		}

		return accountRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Account entity) throws Exception {
		log.debug("deleting Account instance");

		if (entity == null) {
			throw new RuntimeException("The Account is null");
		}

		if (entity.getAccoId() == null) {
			throw new RuntimeException("The Account Id is null");
		}

		if (accountRepository.existsById(entity.getAccoId()) == false) {
			throw new RuntimeException("Account not found");
		}

		findById(entity.getAccoId()).ifPresent(entidad -> {
			List<RegisteredAccount> registeredAccounts = entidad.getRegisteredAccounts();
			if (registeredAccounts!=null && registeredAccounts.isEmpty() == false) {
				throw new RuntimeException("The Account has registeredAccounts");
			}
			List<Transaction> transactions = entidad.getTransactions();
			if (transactions!=null && transactions.isEmpty()==false) {
				throw new RuntimeException("The Account has transactions");
			}
		});

		accountRepository.deleteById(entity.getAccoId());
		log.debug("delete Account successful");

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		log.debug("deleting Account instance");
		if (id == null) {
			throw new RuntimeException("The Id is null");
		}
		Optional<Account> optionalAccount = accountRepository.findById(id);
		if (optionalAccount.isPresent()) {
			delete(optionalAccount.get());
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Account update(Account entity) throws Exception {

		log.debug("updating Account instance");

		if (entity == null) {
			throw new RuntimeException("The Account is null");
		}

		validate(entity);

		if (accountRepository.existsById(entity.getAccoId()) == false) {
			throw new RuntimeException("Account not found");
		}

		return accountRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Account> findById(String accoId) {
		log.debug("getting Account instance");
		return accountRepository.findById(accoId);
	}

}
