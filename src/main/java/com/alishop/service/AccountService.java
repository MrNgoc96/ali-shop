package com.alishop.service;


import com.alishop.dto.AccountDTO;
import com.alishop.entity.Account;
import java.util.List;

public interface AccountService {

    List<AccountDTO> getAccounts(int page, int size);

    AccountDTO getAccount(String username);

    boolean saveAccount(AccountDTO account);

    boolean updateAccount(AccountDTO account,String username);

    void deleteAccount(String username);

    AccountDTO login(String username,String password);
}
