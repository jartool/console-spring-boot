package com.github.jartool.console.queue;

import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Queues;

import java.util.Queue;

/**
 * 并发驱逐队列
 *
 * @author jartool
 * @date 2021/10/15 16:11:13
 */
public class ConcurrentEvictingQueue<T> {

    private Queue<T> queue;

    public ConcurrentEvictingQueue(int maxSize) {
        this.queue = Queues.synchronizedQueue(EvictingQueue.<T>create(maxSize));
    }

    /**
     * push
     * @param t t
     * @return boolean
     */
    public boolean push(T t) {
        return this.queue.offer(t);
    }

    /**
     * poll
     * @return boolean
     */
    public T poll() {
        return this.queue.poll();
    }

    /**
     * isEmpty
     * @return boolean
     */
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }
}
