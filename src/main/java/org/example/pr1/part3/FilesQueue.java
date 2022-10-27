package org.example.pr1.part3;

import java.util.LinkedList;

public class FilesQueue {
    private final LinkedList<MyFile> filesQueue;

    public FilesQueue() {
        this.filesQueue = new LinkedList<>();
    }

    public synchronized MyFile remove() {
        if (filesQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        notify();
        return filesQueue.remove();
    }

    public synchronized void add(MyFile file) {
        if (filesQueue.size() == 5) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        filesQueue.add(file);
        notify();
    }

    public synchronized MyFile peek() {
        if (filesQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        notify();
        return filesQueue.peek();
    }
}
