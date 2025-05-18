package ru.itk.concurrency;

import java.util.ArrayDeque;
import java.util.Queue;

public class BlockingQueue<T> {
    private final int size;
    private final Queue<T> queue;

    public BlockingQueue(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
        this.size = size;
        this.queue = new ArrayDeque<>(size);
    }

    public synchronized void enqueue(T el) throws InterruptedException {
        while (queue.size() == size) {
            wait();
        }
        queue.add(el);
        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T result = queue.poll();
        notifyAll();
        return result;
    }


    public synchronized int size() {
        return queue.size();
    }
}
