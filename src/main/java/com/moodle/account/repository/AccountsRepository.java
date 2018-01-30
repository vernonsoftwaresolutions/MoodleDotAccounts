package com.moodle.account.repository;

import com.moodle.account.model.Account;

import java.util.List;
import java.util.Optional;

/**
 * Created by andrewlarsen on 11/4/17.
 */
public interface AccountsRepository {

    Account save(Account account);
    Optional<Account> getAccount(String email);
    Optional<Account> getAccountById(String id);
    void deleteAccount(Account account);
}
