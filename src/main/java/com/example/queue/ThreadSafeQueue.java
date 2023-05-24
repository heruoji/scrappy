package com.example.queue;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadSafeQueue<T> {
    private Queue<T> queue = new LinkedList<>();
    private boolean isEmpty = true;
    private boolean isTerminate = false;
    private static final int CAPACITY = 100;

    public synchronized void add(T request) {
        while (queue.size() == CAPACITY) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.add(request);
        isEmpty = false;
        notify();
    }

    public synchronized T remove() {
        T element = null;
        while (isEmpty && !isTerminate) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (queue.size() == 1) {
            isEmpty = true;
        }

        if (queue.size() == 0 && isTerminate) {
            return null;
        }

        element = queue.remove();
        if (queue.size() == CAPACITY - 1) {
            notifyAll();
        }

        return element;
    }

    public synchronized void terminate() {
        isTerminate = true;
        notifyAll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized int size() {
        return queue.size();
    }
}
