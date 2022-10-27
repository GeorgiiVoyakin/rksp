package org.example.pr2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task1 {
    public static void main(String[] args) throws IOException {
        String text = "hello\nworld";
        String path = "./text.txt";
        Files.write(Path.of(path), text.getBytes());
        System.out.println(Files.readAllLines(Path.of("./text.txt"), StandardCharsets.UTF_8));
    }
}
