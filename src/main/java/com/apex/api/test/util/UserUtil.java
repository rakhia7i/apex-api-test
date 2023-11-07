package com.apex.api.test.util;

import com.apex.api.test.core.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class UserUtil {

    private static final String FILE_PATH_USER_DOT_JSON = "user.json"; // Only used in this class
    private static ObjectMapper mapper = new ObjectMapper();
    private static Faker faker = new Faker();
    private static String[] genderArr = new String[] {"male", "female"};
    private static String[] statusArr = new String[] {"active", "inactive"};

    private static User user = null;

    /**
     * Creates a random user using values from faker and saves it to the user.json file
     * @throws Exception
     */
    public static void createRandomUser() throws Exception {
        user = new User();
        String firstName = faker.name().firstName(); // Get a random first name from faker
        String lastName = faker.name().lastName(); // Get a random last name from faker

        user.setName(firstName + " " + lastName); // Set the name of the user

        user.setGender(getRandomArrItem(genderArr)); // Set the gender of the user
        user.setStatus(getRandomArrItem(statusArr)); // Set the status of the user
        user.setEmail((firstName + "." + lastName + faker.random().hashCode() + "@apitest.com").toLowerCase());

        // Save the user to the user.json file
        writeUserToFile(user);

    }

    /**
     * Returns a user object. If the user.json file exists, it will return the user from the file.
     * @return
     * @throws Exception
     */
    public static User getUserFromFile() throws Exception {
        return readUserFromFile();
    };


    /**
     * Writes the user object passed in as a parameter to the user.json file
     * @param user
     * @throws IOException
     */
    public static void writeUserToFile(User user) throws IOException {
        Path path = Paths.get(FILE_PATH_USER_DOT_JSON);
        byte[] strToBytes = mapper.writeValueAsString(user).getBytes();

        Files.write(path, strToBytes);
        System.out.println("User written to file: " + FILE_PATH_USER_DOT_JSON + " successfully");
    }

    /**
     * Reads the user.json file and returns a User object with the values from the file
     * @return
     * @throws Exception
     */
    private static User readUserFromFile() throws Exception {
        System.out.println("Reading user from file: " + FILE_PATH_USER_DOT_JSON);
        if(!Files.exists(Paths.get(FILE_PATH_USER_DOT_JSON))){
            new RuntimeException("File does not exist. Please ensure that you have created a user first");
        }

        Path path = Paths.get(FILE_PATH_USER_DOT_JSON);
        byte[] bytes = Files.readAllBytes(path);
        String userString = new String(bytes);

        // Using the ObjectMapper to convert the JSON string to a User object
        User user = mapper.readValue(userString, User.class);

        return user;
    }

    /**
     * Returns a random element from the array passed in as a parameter
     * @param arr
     * @return String
     */
    private static String getRandomArrItem(String[] arr) {
        Random random = new Random();
        // 'nextInt' returns a random number between 0 (inclusive) and the number passed in as a parameter (exclusive)
        return arr[random.nextInt(arr.length)];
    }

    /**
     * Helper method to generate a new random name using faker library
     * @return
     */
    public static String generateNewName() {

        return faker.name().fullName();
    }

    public static String[][] postUsersIntoExcel(String s, String sheet1, String postValidIds) {
        String[][] ids = POIExcelUtil.getUsersFromExcel("ApexTestData.xlsx", "Sheet2", "postValidIds");


        return ids;


    }


}

