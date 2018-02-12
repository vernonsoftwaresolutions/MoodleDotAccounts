package com.moodle.account.mapper;

import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.GetUserResult;
import com.moodle.account.LambdaHandler;
import com.moodle.account.model.AccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by andrewlarsen on 2/11/18.
 */
@Component
public class CognitoUserMapper {
    private Logger log = LoggerFactory.getLogger(LambdaHandler.class);

    private static final String ADDRESS = "address";
    private static final String NAME = "name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String EMAIL = "email";

    /**
     * Method to convert GetUserResult class to CognitoUser
     * @param result
     * @return
     */
    public AccountDTO from(GetUserResult result) {

        AccountDTO user = new AccountDTO();

        user.setUserName(result.getUsername());

        List<AttributeType> attributeTypes = result.getUserAttributes();

        attributeTypes.forEach(attributeType -> {
            String attributeName = attributeType.getName();
            String attributeValue = attributeType.getValue();
            log.debug("processing attributeType for match with name {} and value {}", attributeName, attributeValue);

            switch (attributeName) {
                case ADDRESS:
                    user.setAddress(attributeValue);
                    break;
                case NAME:
                    user.setName(attributeValue);
                    break;
                case PHONE_NUMBER:
                    user.setPhoneNumber(attributeValue);
                    break;
                case EMAIL:
                    user.setEmail(attributeValue);
                    break;
                default:
                  log.debug("missed on name {}", attributeName);
            }
        });
        log.debug("Created CognitoUser {} ", user);
        return user;
    }
}
