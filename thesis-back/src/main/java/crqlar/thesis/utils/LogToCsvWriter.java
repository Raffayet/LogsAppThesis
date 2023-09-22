package crqlar.thesis.utils;

import crqlar.thesis.dto.LogDTO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LogToCsvWriter {

    private static String csvFilePath = "src/main/resources/data/generated-logs.csv";
    private static boolean isHeaderWritten = false;

    public static void writeNewLog(LogDTO inputLog) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, true))) { // Use append mode
            if (!isHeaderWritten) {
                List<String> header = Arrays.asList("LogType", "Timestamp", "LogValue");
                writeRow(writer, header);
                isHeaderWritten = true;
            }

            List<String> log = Arrays.asList(
                    inputLog.getLogType(),
                    String.valueOf(inputLog.getTimestamp()),
                    String.valueOf(inputLog.getLogValue())
            );

            writeRow(writer, log);
            System.out.println("Log has been appended to " + csvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeRow(BufferedWriter writer, List<String> row) throws IOException {
        String rowString = String.join(",", row);
        writer.write(rowString);
        writer.newLine();
    }
}
