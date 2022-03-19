package co.edu.eafit.bank.service;

import co.edu.eafit.bank.dto.DepositDTO;
import co.edu.eafit.bank.dto.TransactionResultDTO;
import co.edu.eafit.bank.dto.TransferDTO;
import co.edu.eafit.bank.dto.WithdrawDTO;

public interface BankTransactionService {

	TransactionResultDTO withdraw(WithdrawDTO withdrawDTO) throws Exception;

	TransactionResultDTO deposit(DepositDTO depositDTO) throws Exception;

	TransactionResultDTO transfer(TransferDTO transferDTO) throws Exception;

}
