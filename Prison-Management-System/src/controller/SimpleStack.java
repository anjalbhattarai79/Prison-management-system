package controller;

/**
 * SimpleStack - simple LIFO stack using a fixed array.
 * Demonstrates explicit overflow (max size reached) and underflow (empty stack).
 */
public class SimpleStack {
    private static final int MAX_SIZE = 5; // required max size per spec
    private final Object[] elements = new Object[MAX_SIZE];
    private int top = -1; // -1 when empty

    public void push(Object item) {
        if (top == MAX_SIZE - 1) {
            throw new IllegalStateException("Stack overflow");
        }
        top++;
        elements[top] = item;
    }

    public Object pop() {
        if (top == -1) {
            throw new IllegalStateException("Stack underflow");
        }
        Object removed = elements[top];
        elements[top] = null;
        top--;
        return removed;
    }

    public Object peek() {
        if (top == -1) {
            return null;
        }
        return elements[top];
    }

    /** Alias for clarity: top of the stack (same as peek). */
    public Object top() {
        return peek();
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    public void clear() {
        while (top >= 0) {
            elements[top] = null;
            top--;
        }
    }

    /**
     * Return contents as array in insertion order (bottom → top).
     * The last element of the returned array represents the top.
     */
    @SuppressWarnings("unchecked")
    public <U> U[] toArray(U[] a) {
        int n = size();
        if (a.length < n) {
            Class<?> component = a.getClass().getComponentType();
            U[] r = (U[]) java.lang.reflect.Array.newInstance(component, n);
            for (int i = 0; i < n; i++) {
                r[i] = (U) elements[i];
            }
            return r;
        } else {
            for (int i = 0; i < n; i++) {
                a[i] = (U) elements[i];
            }
            if (a.length > n) {
                a[n] = null;
            }
            return a;
        }
    }
}
