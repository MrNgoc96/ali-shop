package com.alishop.service.impl;

import com.alishop.bases.BaseUtils;
import com.alishop.bases.TransformUtils;
import com.alishop.dto.AccountDTO;
import com.alishop.entity.Account;
import com.alishop.repository.AccountRepository;
import com.alishop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransformUtils<Account, AccountDTO> transformUtils;

    @Override
    public AccountDTO login(String username, String password) {
        password = BaseUtils.encryptPassword(password);
        Account account = accountRepository.login(username, password);
        if (account == null) return null;
        return transformUtils.transform(account, AccountDTO.class);
    }

    @Override
    public List<AccountDTO> getAccounts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Account> accountList = accountRepository.findAll(pageRequest).getContent();
        return transformUtils.transformList(accountList, AccountDTO.class);
    }

    @Override
    public AccountDTO getAccount(String username) {
        Account account = accountRepository.getOne(username);
        return transformUtils.transform(account, AccountDTO.class);
    }

    @Override
    public boolean saveAccount(AccountDTO accountDTO) {
        Account account = transformUtils.transformReverse(accountDTO, Account.class);
        return accountRepository.save(account) != null;
    }

    @Override
    public boolean updateAccount(AccountDTO accountDTO, String username) {
        if (getAccount(username) == null) return false;
        Account account = transformUtils.transformReverse(accountDTO, Account.class);
        return accountRepository.save(account) != null;
    }

    @Override
    public void deleteAccount(String username) {
        accountRepository.deleteById(username);
    }
}
