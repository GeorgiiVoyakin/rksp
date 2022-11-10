package org.example.pr2;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.nio.file.StandardWatchEventKinds.*;

public class Task4 {
    private static final Map<String, List<String>> filesContent = new HashMap<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(".\\TEST");
        readAllFiles(path);
        //будем следить за созданием, изменение и удалением файлов.
        path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
        boolean poll = true;
        while (poll) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Event kind : " + event.kind() + " - File : " + event.context());
                if (event.kind() == ENTRY_CREATE) {
                    List<String> content = Files.readAllLines(Path.of(path + "\\" + event.context()));
                    filesContent.put(event.context().toString(), content);
                } else if (event.kind() == ENTRY_MODIFY) {
                    List<String> newContent = Files.readAllLines(Path.of(path + "\\" + event.context()));
                    List<String> oldContent =  filesContent.get(event.context().toString());
                    filesContent.put(event.context().toString(), newContent);
                    for (int i = 0; i < max(oldContent.size(), newContent.size()); i++) {
                        try {
                            if (!oldContent.get(i).equals(newContent.get(i))) {
                                System.out.println("Diff in line " + (i + 1) + " : " + oldContent.get(i) + " -> " + newContent.get(i));
                            }
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("New line: " + newContent.get(i));
                        }
                    }
                }
            }
            poll = key.reset();
        }
    }

    private static void readAllFiles(Path path) throws IOException {
        try (Stream<Path> paths = Files.walk(path)) {
            paths
                .filter(Files::isRegularFile)
                .forEach((var it) -> {
                    try {
                        Task4.filesContent.put(it.getFileName().toString(), Files.readAllLines(it));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        }
    }
}
