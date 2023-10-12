package com.apex.api.test.util;

import com.apex.api.test.core.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    public static final String FILE_PATH_USER_DOT_JSON = "user.json";
    private static ObjectMapper mapper = new ObjectMapper();
    public static void writeUserTOFile(User user) throws IOException {
        Path path = Paths.get(FILE_PATH_USER_DOT_JSON);
        byte[] strToBytes = mapper.writeValueAsString(user).getBytes();

        Files.write(path, strToBytes);
        System.out.println("User written to file" + FILE_PATH_USER_DOT_JSON + " successfully");
    }

    public static User readUserFromFile() throws IOException {
        System.out.println("Reading user from file" + FILE_PATH_USER_DOT_JSON);
        Path path = Paths.get(FILE_PATH_USER_DOT_JSON);
        byte[] bytes = Files.readAllBytes(path);
        String userString = new String(bytes);
        User user = mapper.readValue(userString, User.class);
        return user;
    }
}
