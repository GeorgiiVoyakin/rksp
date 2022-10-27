package org.example.pr1.part3;

public class Task3 {
    public static void main(String[] args) {
        FilesQueue files = new FilesQueue();
        FileGenerator fg = new FileGenerator(10, files);
        FileProcessor fpJSON = new FileProcessor(files, MyFile.FileType.JSON);
        FileProcessor fpXML = new FileProcessor(files, MyFile.FileType.XML);
        FileProcessor fpXLS = new FileProcessor(files, MyFile.FileType.XLS);
        fg.start();
        fpJSON.start();
        fpXML.start();
        fpXLS.start();
    }
}
