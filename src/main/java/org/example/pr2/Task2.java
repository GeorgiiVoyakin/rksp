package org.example.pr2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Task2 {
    public static void main(String[] args) throws IOException {
        // 1
        File file = new File("./100MB.txt");
        File copy = new File("./copy.txt");
        try (FileInputStream in = new FileInputStream(file);
             FileOutputStream out = new FileOutputStream(copy)
        ) {
            out.write(in.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 2
        String copy1PathString = "./copy1.txt";
        Path copy1Path = Path.of(copy1PathString);
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

        // 3


        // 4
        Files.copy(file.toPath(), Path.of("./copy2.txt"));
    }
}
