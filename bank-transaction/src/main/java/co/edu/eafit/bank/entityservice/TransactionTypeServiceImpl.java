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

import co.edu.eafit.bank.domain.Transaction;
import co.edu.eafit.bank.domain.TransactionType;
import co.edu.eafit.bank.repository.TransactionTypeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 * 
 */

@Scope("singleton")
@Service
@Slf4j
public class TransactionTypeServiceImpl implements TransactionTypeService {

	@Autowired
	private TransactionTypeRepository transactionTypeRepository;

	@Autowired
	private Validator validator;

	@Override
	public void validate(TransactionType transactionType) throws ConstraintViolationException {

		Set<ConstraintViolation<TransactionType>> constraintViolations = validator.validate(transactionType);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(constraintViolations);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return transactionTypeRepository.count();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TransactionType> findAll() {
		log.debug("finding all TransactionType instances");
		return transactionTypeRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public TransactionType save(TransactionType entity) throws Exception {
		log.debug("saving TransactionType instance");

		if (entity == null) {
			throw new RuntimeException("The TransactionType is null");
		}

		validate(entity);

		if (transactionTypeRepository.existsById(entity.getTrtyId())) {
			throw new RuntimeException("The TransactionType already exists");
		}

		return transactionTypeRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(TransactionType entity) throws Exception {
		log.debug("deleting TransactionType instance");

		if (entity == null) {
			throw new RuntimeException("The TransactionType is null");
		}

		if (entity.getTrtyId() == null) {
			throw new RuntimeException("The Id is null");
		}

		if (transactionTypeRepository.existsById(entity.getTrtyId()) == false) {
			throw new RuntimeException("TransactionType not found");
		}

		findById(entity.getTrtyId()).ifPresent(entidad -> {
			List<Transaction> transactions = entidad.getTransactions();
			if (transactions!=null && transactions.isEmpty()== false) {
				throw new RuntimeException("The TransactionType has transactions");
			}
		});

		transactionTypeRepository.deleteById(entity.getTrtyId());
		log.debug("delete TransactionType successful");

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {
		log.debug("deleting TransactionType instance");
		if (id == null) {
			throw new RuntimeException("The TransactionType is null");
		}
		Optional<TransactionType> optionalTransactionType = transactionTypeRepository.findById(id);
		if (optionalTransactionType.isPresent()) {
			delete(optionalTransactionType.get());
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public TransactionType update(TransactionType entity) throws Exception {

		log.debug("updating TransactionType instance");

		if (entity == null) {
			throw new RuntimeException("The TransactionType is null");
		}

		validate(entity);

		if (transactionTypeRepository.existsById(entity.getTrtyId()) == false) {
			throw new RuntimeException("TransactionType not found");
		}

		return transactionTypeRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<TransactionType> findById(Integer trtyId) {
		log.debug("getting TransactionType instance");
		return transactionTypeRepository.findById(trtyId);
	}

}
