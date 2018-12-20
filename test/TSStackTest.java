import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class TSStackTest {
    private TSStack<Integer> tsStack;

    @BeforeEach
    void setUp() {
        tsStack = new TSStack<>();
    }


    @Test
    void push() {
        tsStack.push(8);
        assertEquals(8, (int) tsStack.peek());
        assertThrows(NullPointerException.class, () -> tsStack.push(null));
    }

    @Test
    void pop() {
        assertThrows(NoSuchElementException.class, () -> tsStack.pop());
        tsStack.push(25);
        tsStack.push(7);
        assertEquals(7, (int) tsStack.pop());
        assertEquals(25, (int) tsStack.pop());
    }

    @Test
    void peek() {
        assertNull(tsStack.peek());
        tsStack.push(35);
        assertEquals(35, (int) tsStack.peek());
    }

    @Test
    void size() {
        assertEquals(0, tsStack.size());
        tsStack.push(1);
        tsStack.push(2);
        assertEquals(2, tsStack.size());
    }

    @Test
    void isEmpty() {
        assertTrue(tsStack.isEmpty());
        tsStack.push(2);
        assertFalse(tsStack.isEmpty());
    }

    @RepeatedTest(10)
    void threadSafetyTest() {
        CountDownLatch latch = new CountDownLatch(1);
        Thread t1 = new Thread(() -> {
            try {
                latch.await();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 50; i += 3) {
                tsStack.push(i);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                latch.await();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 1; i < 50; i += 3) {
                tsStack.push(i);
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                latch.await();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 2; i < 50; i += 3) {
                tsStack.push(i);
            }
        });
        t1.start();
        t2.start();
        t3.start();
        latch.countDown();
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(50, tsStack.size());
    }


}