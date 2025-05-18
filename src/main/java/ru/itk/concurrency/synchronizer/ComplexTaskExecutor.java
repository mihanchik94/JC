package ru.itk.concurrency.synchronizer;

import lombok.AllArgsConstructor;

import java.util.concurrent.*;

@AllArgsConstructor
public class ComplexTaskExecutor {
    private final int numOfThreads;


    public void executeTasks(int numberOfTasks) {
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(numOfThreads, this::mergeResults);
        for (int i = 0; i < numberOfTasks; i++) {
            final int finalI = i;
            executorService.execute(() -> {
                ComplexTask complexTask = new ComplexTask(finalI);
                complexTask.execute();
                try {
                    cyclicBarrier.await();
                } catch (BrokenBarrierException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private void mergeResults() {
        System.out.println("Merging of task results has begun");
    }
}
