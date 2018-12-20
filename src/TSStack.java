import java.util.ArrayDeque;

public class TSStack<T> {
    private ArrayDeque<T> stack;

    public TSStack() {
        this.stack = new ArrayDeque<>();
    }

    public synchronized void push(T element) {
        this.stack.push(element);
    }

    public synchronized T pop() {
        return this.stack.pop();
    }

    public synchronized T peek() {
        return this.stack.peek();
    }

    public synchronized int size() {
        return this.stack.size();
    }

    public synchronized boolean isEmpty() {
        return this.stack.isEmpty();
    }
}
