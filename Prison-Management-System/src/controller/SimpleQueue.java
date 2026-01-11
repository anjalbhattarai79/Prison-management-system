package controller;

import java.util.ArrayList;

/**
 * SimpleQueue - minimal FIFO queue built on ArrayList with a head index.
 * Enqueue: append; Dequeue: advance head; Compact occasionally to avoid growth.
 */
public class SimpleQueue {
    private final ArrayList<Object> items = new ArrayList<>();
    private int head = 0; // front index
    private int rear = -1; // rear index (last valid element)

    public void enqueue(Object item) {
        items.add(item);
        rear = items.size() - 1; // update rear on enqueue
    }

    public Object dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        Object value = items.get(head);
        head++;
        resetIfEmpty();
        compactIfNeeded();
        return value;
    }

    public Object peek() {
        if (isEmpty()) {
            return null;
        }
        return items.get(head);
    }

    /** Alias for clarity: front element (same as peek). */
    public Object front() {
        return peek();
    }

    /** Rear element without removal. */
    public Object rear() {
        if (isEmpty()) {
            return null;
        }
        return items.get(rear);
    }

    public boolean isEmpty() {
        return head >= items.size();
    }

    public int size() {
        return items.size() - head;
    }

    public void clear() {
        items.clear();
        head = 0;
        rear = -1;
    }

    @SuppressWarnings("unchecked")
    public <U> U[] toArray(U[] a) {
        int n = size();
        ArrayList<Object> slice = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            slice.add(items.get(head + i));
        }
        U[] out = a;
        if (a.length < n) {
            Class<?> component = a.getClass().getComponentType();
            out = (U[]) java.lang.reflect.Array.newInstance(component, n);
        }
        for (int i = 0; i < n; i++) {
            out[i] = (U) slice.get(i);
        }
        if (out.length > n) out[n] = null;
        return out;
    }

    // Compact underlying storage when head crosses a threshold
    private void compactIfNeeded() {
        int n = items.size();
        // Compact when head is at least 64 and at least half of the array is consumed
        if (head >= 64 && head * 2 >= n) {
            ArrayList<Object> compacted = new ArrayList<>(n - head);
            for (int i = head; i < n; i++) {
                compacted.add(items.get(i));
            }
            items.clear();
            items.addAll(compacted);
            head = 0;
            rear = items.size() - 1;
        }
    }

    // Reset indices when queue becomes empty after dequeues
    private void resetIfEmpty() {
        if (head >= items.size()) {
            items.clear();
            head = 0;
            rear = -1;
        }
    }
}
