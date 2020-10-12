package com.react.task;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUtils {

    private static final String FILE_NAME = "calculation_result.txt";
    private static final File file = new File(FILE_NAME);

    static File saveTextToFile(String textToSave) {
        try {
            boolean isNewFile = file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            if (isNewFile) {
                writer.print("");
            }
            writer.println(textToSave);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
