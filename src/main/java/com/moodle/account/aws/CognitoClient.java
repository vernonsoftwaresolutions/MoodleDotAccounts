package com.moodle.account.aws;

import com.moodle.account.model.AccountDTO;

/**
 * Created by andrewlarsen on 2/11/18.
 */
public interface CognitoClient {

    AccountDTO getUser(String token);
}
