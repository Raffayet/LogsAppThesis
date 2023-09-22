package crqlar.thesis.utils;

import java.util.Random;

public class NumberGenerator {
    public static void main(String[] args) {
        int randomId = generateRandomId();
        System.out.println("Random ID: " + randomId);
    }

    public static int generateRandomId() {
        // Create a Random object
        Random random = new Random();

        // Generate a random integer ID within a desired range
        int minId = 1000; // Minimum ID value
        int maxId = 9999; // Maximum ID value

        // Calculate the random ID within the specified range
        int randomId = random.nextInt(maxId - minId + 1) + minId;

        return randomId;
    }

    public static int generateLogValue() {
        // Create a Random object
        Random random = new Random();

        // Generate a random integer ID within a desired range
        int minId = 500; // Minimum ID value
        int maxId = 3500; // Maximum ID value

        // Calculate the random ID within the specified range
        int randomId = random.nextInt(maxId - minId + 1) + minId;

        return randomId;
    }
}

