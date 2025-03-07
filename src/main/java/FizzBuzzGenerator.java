import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class FizzBuzzGenerator {
    private volatile AtomicInteger counter = new AtomicInteger();
    private volatile AtomicInteger processingThreads = new AtomicInteger();
    private volatile String[] cache = new String[3];
    private final Object lock = new Object();
    private int finish;

    public void generate(int n) {
        if (n < 1) {
            System.out.println("Calculates only for values >=1 !");
            return;
        }

        counter.set(1);
        processingThreads.set(3);
        finish = n;

        List<Function<Integer, String>> allFunctions = Arrays.asList(
                this::fizz,
                this::buzz,
                this::fizzbuzz
        );

        Thread ThreadA = new Thread(() -> processNumbers(allFunctions.get(0), 0));
        Thread ThreadB = new Thread(() -> processNumbers(allFunctions.get(1), 1));
        Thread ThreadC = new Thread(() -> processNumbers(allFunctions.get(2), 2));
        Thread ThreadD = new Thread(this::processOutput);

        ThreadA.start();
        ThreadB.start();
        ThreadC.start();
        ThreadD.start();

        try {
            ThreadA.join();
            ThreadB.join();
            ThreadC.join();
            ThreadD.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processNumbers(Function<Integer, String> function, int cacheIndex) {
        while (true) {
            int currentNumber = counter.get();

            if (currentNumber > finish) {
                break;
            }

            cache[cacheIndex] = function.apply(currentNumber);

            int remainingThreads = processingThreads.decrementAndGet();
            if (remainingThreads == 0) {
                synchronized (lock) {
                    lock.notifyAll();
                }
            }

            synchronized (lock) {
                try {
                    while (counter.get() == currentNumber && currentNumber <= finish) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    private void processOutput() {
        while (true) {
            String output = "";
            int currentNumber = counter.get();

            if (currentNumber > finish) {
                break;
            }

            synchronized (lock) {
                try {
                    while (processingThreads.get() > 0 && currentNumber <= finish) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    return;
                }

                for (String value : cache) {
                    if (!value.isEmpty()) {
                        output = value;
                        break;
                    }
                }

                number(output.isEmpty() ? ("" + currentNumber) : output);

                Arrays.fill(cache, "");
                counter.incrementAndGet();
                processingThreads.set(3);

                lock.notifyAll();
            }
        }
    }

    private String fizz(int number) {
        return number % 3 == 0 && number % 5 != 0 ? "fizz" : "";
    }

    private String buzz(int number) {
        return number % 5 == 0 && number % 3 != 0 ? "buzz" : "";
    }

    private String fizzbuzz(int number) {
        return number % 3 == 0 && number % 5 == 0 ? "fizzbuzz" : "";
    }

    private void number(String number) {
        System.out.print(number + (counter.get() == finish ? "\n" : ", "));
    }
}