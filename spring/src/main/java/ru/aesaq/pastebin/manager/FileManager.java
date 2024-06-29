package ru.aesaq.pastebin.manager;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileManager {
    public String getPostContent(String hash) {
        String fileName = hash + ".txt";
        try {
            return Files.readString(Paths.get("src/main/resources/texts/" + fileName));
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return "ERROR";
    }

    public void savePostContent(String hash, String content) {
        String fileName = hash + ".txt";
        File file = new File("src/main/resources/texts", fileName);
        try (FileWriter fileWriter = new FileWriter("src/main/resources/texts/" + fileName)) {
            file.createNewFile();
            fileWriter.write(content);
            System.out.println("File created: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error occurred while creating file: " + e.getMessage());
        }
    }

    public void editPostContent(String hash, String newContent) {
        String filePath = "src/main/resources/texts/" + hash + ".txt";
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(newContent);
            System.out.println("File edited: " + filePath);
        } catch (IOException e) {
            System.err.println("Error occurred while editing file: " + e.getMessage());
        }
    }

    public void deletePostContent(String hash) {
        String filePath = "src/main/resources/texts/" + hash + ".txt";
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("File not found: " + filePath);
            return;
        }
        if (!file.isFile()) {
            System.err.println("It's not a file: " + file.getAbsolutePath());
            return;
        }
        if (file.delete()) {
            System.out.println("File deleted: " + file.getAbsolutePath());
        } else {
            System.err.println("Error occurred while deleting file: " + file.getAbsolutePath());
        }
    }

}
