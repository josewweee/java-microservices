package co.edu.eafit.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.eafit.bank.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	List<Customer> findByName(String name);
	
	@Query("SELECT cust FROM Customer cust WHERE cust.documentType.dotyId=?1")
	List<Customer> findByDocumentType(Integer dotyId);
	
}
