package co.edu.eafit.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.eafit.bank.dto.BalanceDTO;

@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@GetMapping("/find/{id}")
	public BalanceDTO find(@PathVariable("id") String id) {
		
		String query=String.format("SELECT balance FROM public.account WHERE acco_id='%s'", id);
		Double balance = jdbcTemplate.queryForObject(query, Double.class);
		return new BalanceDTO(balance);
	}

}
