package com.zb.Account.service;

import com.zb.Account.domain.Account;
import com.zb.Account.domain.AccountUser;
import com.zb.Account.dto.TransactionDto;
import com.zb.Account.exception.AccountException;
import com.zb.Account.repository.AccountRepository;
import com.zb.Account.repository.AccountUserRepository;
import com.zb.Account.repository.TransactionRepository;
import com.zb.Account.type.AccountStatus;
import com.zb.Account.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountUserRepository accountUserRepository;
    private final AccountRepository accountRepository;

    /**
     *
     * 사용자가 없는 경우, 사용자 아이디와 계좌 소유주가 다른 경우,
     * 계좌가 이미 해지 상태인 경우, 거래 금액이 잔액보다 큰 경우,
     * 거래 금액이 너무 작거나 큰 경우 실패 응답
     */
    @Transactional
    public void useBalance(Long userId, String accountNumber, Long amount){
        AccountUser user = accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(ErrorCode.USER_NOT_FOUND));
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));
        validateUseBalance(user, account, amount);
    }

    private void validateUseBalance(AccountUser user, Account account, Long amount){
        if(!Objects.equals(user.getId(), account.getAccountUser().getId())){
            throw new AccountException(ErrorCode.USER_ACCOUNT_UNMATCH);
        }
        if(account.getAccountStatus() != AccountStatus.IN_USE){
            throw new AccountException(ErrorCode.ACCOUNT_ALREADY_UNREGISTERED);
        }
        if(account.getBalance() < amount){
            throw new AccountException(ErrorCode.AMOUNT_EXCEED_BALANCE);
        }
    }
}
