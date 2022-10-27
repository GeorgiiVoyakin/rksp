package org.example.pr1.part3;

import java.util.Random;

public class FileGenerator extends Thread {
    private final int filesAmount;
    private final FilesQueue files;

    FileGenerator(int filesAmount, FilesQueue files) {
        this.filesAmount = filesAmount;
        this.files = files;
    }

    @Override
    public void run() {
        for (int i = 0; i < filesAmount; i++) {
            Random random = new Random();
            int choice = random.nextInt(3);
            int size = random.nextInt(91) + 10;
            int time = random.nextInt(901) + 100;
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            MyFile file = null;
            switch (choice) {
                case 0 -> {
                    file = new MyFile(MyFile.FileType.JSON, size);
                }
                case 1 -> {
                    file = new MyFile(MyFile.FileType.XML, size);
                }
                case 2 -> {
                    file = new MyFile(MyFile.FileType.XLS, size);
                }
            }
            try {
                Thread.sleep(file.size() * 7L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            files.add(file);
            System.out.println("File generator> " + file);
        }
    }
}
