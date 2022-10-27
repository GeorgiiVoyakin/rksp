package org.example.pr1.part3;

public record MyFile(org.example.pr1.part3.MyFile.FileType type, int size) {
    enum FileType {
        XML,
        JSON,
        XLS
    }
}
