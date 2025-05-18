package ru.itk.concurrency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockingQueueTest {
    private BlockingQueue<Integer> blockingQueue;

    @BeforeEach
    public void setUp() {
        blockingQueue = new BlockingQueue<>(2);
    }

    @Test
    public void whenEnqueueAndDequeueAndSizeThenCorrectResults() throws InterruptedException {
        blockingQueue.enqueue(1);
        blockingQueue.enqueue(2);
        assertEquals(1, blockingQueue.dequeue());
        assertEquals(2, blockingQueue.dequeue());
        assertEquals(0, blockingQueue.size());
    }


    @Test
    public void whenSizeThenGetSize() throws InterruptedException {
        assertEquals(0, blockingQueue.size());
        blockingQueue.enqueue(10);

        assertEquals(1, blockingQueue.size());
        blockingQueue.enqueue(20);

        assertEquals(2, blockingQueue.size());

        blockingQueue.dequeue();
        assertEquals(1, blockingQueue.size());

        blockingQueue.dequeue();
        assertEquals(0, blockingQueue.size());
    }

    @Test
    public void whenSizeIs0ThenException() {
        assertThrows(IllegalArgumentException.class,
                () -> new BlockingQueue<>(0));
    }
}