package client;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

@SpringBootApplication
public class ClientApplication {
    public static void main(String[] args) {
        String filePath;
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("Enter a valid file path: ");
            filePath = scanner.nextLine();
            if(isGoodFile(filePath)) {
                scanner.close();
                break;
            }
        }
        Application.launch(MainApp.class, filePath);
    }

    public static boolean isGoodFile(String filePath){
        Path file = Paths.get(filePath);
        if(Files.exists(file) && Files.isRegularFile(file))
            if(Files.isWritable(file)) {
                //Get file extension
                int dotIndex = filePath.lastIndexOf(".");
                if(dotIndex > 0 & dotIndex < filePath.length() - 1){
                    String fileExtension = filePath.substring(dotIndex + 1);
                    if(fileExtension.equalsIgnoreCase("json"))
                        return true;
                }

            }
        return false;
    }
}
