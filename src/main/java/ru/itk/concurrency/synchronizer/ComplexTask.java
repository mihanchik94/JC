package ru.itk.concurrency.synchronizer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ComplexTask {
    private int taskId;

    public synchronized void execute() {
        try {
            System.out.printf("%s is started execution of task %d%n", Thread.currentThread().getName(), taskId);
            Thread.sleep((int) (Math.random() * 500));
            System.out.printf("Task %d was finished by thread %s%n", taskId, Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
