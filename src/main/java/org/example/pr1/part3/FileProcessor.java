package org.example.pr1.part3;

public class FileProcessor extends Thread {
    private final FilesQueue files;
    private final MyFile.FileType fileType;

    public FileProcessor(FilesQueue files, MyFile.FileType fileType) {
        this.files = files;
        this.fileType = fileType;
    }

    @Override
    public void run() {
        while (true) {
            if (files.peek().type() == fileType) {
                try {
                    Thread.sleep(files.peek().size() * 7L);
                    System.out.println("File processor> " + files.remove());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}