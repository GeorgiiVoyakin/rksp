package org.example.pr1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;

public class FindMinRecursiveTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 10;
    private final int[] array;

    public FindMinRecursiveTask(int[] array) {
        this.array = array;
    }

    @Override
    protected Integer compute() {
        if (array.length > THRESHOLD) {
            return invokeAll(createSubTask())
                .stream()
                .map(ForkJoinTask::join)
                .min(Integer::compare)
                .orElseThrow();
        } else {
            return find(array);
        }
    }

    private Collection<FindMinRecursiveTask> createSubTask() {
        List<FindMinRecursiveTask> dividedTasks = new ArrayList<>();
        dividedTasks.add(new FindMinRecursiveTask(copyOfRange(array, 0, array.length / 2)));
        dividedTasks.add(new FindMinRecursiveTask(copyOfRange(array, array.length / 2, array.length)));
        return dividedTasks;
    }

    private Integer find(int[] array) {
        try {
            Thread.sleep(THRESHOLD);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return stream(array).min().orElseThrow();
    }
}
