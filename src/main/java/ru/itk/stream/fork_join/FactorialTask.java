package ru.itk.stream.fork_join;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<BigInteger> {
    private final long factorial;

    public FactorialTask(long factorial) {
        if (factorial < 0) {
            throw new IllegalArgumentException("Factorial must be positive");
        }
        this.factorial = factorial;
    }

    @Override
    protected BigInteger compute() {
        if (factorial == 0 || factorial == 1) {
            return BigInteger.ONE;
        }
        FactorialTask task = new FactorialTask(factorial - 1);
        task.fork();
        return task.join().multiply(BigInteger.valueOf(factorial));
    }
}
