package org.example.pr2;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class Task2 {
    public static void main(String[] args) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        // 1
        File file = new File("./100MB.txt");
        File copy = new File("./copy.txt");
        long start = System.currentTimeMillis();
        try (FileInputStream in = new FileInputStream(file);
             FileOutputStream out = new FileOutputStream(copy)
        ) {
            out.write(in.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.printf("FileInputStream/FileOutputStream: %d ms %d mb\n", timeElapsed, (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));

        // 2
        String copy1PathString = "./copy1.txt";
        Path copy1Path = Path.of(copy1PathString);
        start = System.currentTimeMillis();
        try (
            var in = FileChannel.open(Path.of(file.getPath()));
            var out = FileChannel.open(copy1Path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(100 * 1024 * 1024);
            in.read(buffer);
            buffer.flip();
            out.write(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finish = System.currentTimeMillis();
        timeElapsed = finish - start;
        System.out.printf("FileChannel: %d ms %d mb\n", timeElapsed, (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));

        // 3
        File copy2 = new File("./copy2.txt");
        start = System.currentTimeMillis();
        List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
        FileUtils.writeLines(copy2, lines);
        finish = System.currentTimeMillis();
        timeElapsed = finish - start;
        System.out.printf("Apache Commons IO: %d ms %d mb\n", timeElapsed, (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));

        // 4
        start = System.currentTimeMillis();
        Files.copy(file.toPath(), Path.of("./copy3.txt"), StandardCopyOption.REPLACE_EXISTING);
        finish = System.currentTimeMillis();
        timeElapsed = finish - start;
        System.out.printf("Files class: %d ms %d mb\n", timeElapsed, (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));
    }
}
