package com.moodle.account.repository;

import com.moodle.account.model.Account;

/**
 * Created by andrewlarsen on 11/4/17.
 */
public interface AccountsRepository {

    Account save(Account account);
}
