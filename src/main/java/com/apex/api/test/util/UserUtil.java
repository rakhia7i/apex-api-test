package com.apex.api.test.util;

import com.apex.api.test.core.User;
import com.github.javafaker.Faker;

public class UserUtil {

    private static Faker faker = new Faker();
    private static String[] genderArr = new String[] {"male", "female"};
    private static String[] statusArr = new String[] {"active", "inactive"};

    private static User user = null;

    private static void init() throws Exception {
        user = new User();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        user.setName(firstName + " " + lastName);
        user.setGender(getRandomArrItem(genderArr));
        user.setStatus(getRandomArrItem(statusArr));
        user.setEmail((firstName + "." + lastName + faker.random().hashCode() + "@apitest.com").toLowerCase());

        FileUtil.writeUserTOFile(user);

    }

    public static User getFakeUser() throws Exception {
        if(user == null) {
            init();
        }

        return FileUtil.readUserFromFile();
    };

    private static String getRandomArrItem(String[] arr) {
        // Math.random(n) returns a random number between 0 and n-1
        return arr[(int) (Math.random() * arr.length)];
    }
}
