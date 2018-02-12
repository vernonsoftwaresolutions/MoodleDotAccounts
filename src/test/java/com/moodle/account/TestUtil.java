package com.moodle.account;

import com.amazonaws.services.cognitoidp.model.GetUserResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by andrewlarsen on 2/11/18.
 */
public class TestUtil {
    public static String userName = "larse514";
    public static String address = "1025 washington ave S";
    public static String name = "Andrew Larsen";
    public static String phoneNumber = "+6514898304";
    public static String email = "alars84@gmail.com";
    public static GetUserResult createGetUserResult() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        GetUserResult result = objectMapper.readValue("{\n" +
                "\t\"username\": \""+ userName +"\",\n" +
                "\t\"userAttributes\": [{\n" +
                "\t\t\t\"name\": \"sub\",\n" +
                "\t\t\t\"value\": \"8487 d853 - 9259 - 4187 - 87 ce - 33 b8554c34e1\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"address\",\n" +
                "\t\t\t\"value\": \""+address+"\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"email_verified\",\n" +
                "\t\t\t\"value\": true\n" +
                "\t\t}, {\n" +
                "\t\t\t\"name\": \"name\",\n" +
                "\t\t\t\"value\": \""+name+"\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"phone_number_verified\",\n" +
                "\t\t\t\"value\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"name\": \"phone_number\",\n" +
                "\t\t\t\"value\": \""+phoneNumber+"\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"name\": \"email\",\n" +
                "\t\t\t\"value\": \""+email+"\"\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}", GetUserResult.class);

        return result;
    }
}
