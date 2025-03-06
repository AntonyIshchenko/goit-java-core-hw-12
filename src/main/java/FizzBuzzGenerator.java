import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class FizzBuzzGenerator{
    private volatile AtomicInteger counter = new AtomicInteger();
    private volatile String[] cache = new String[3];
    private int finish;
    private final String delimiter = ", ";

    public void generate(int n)  {
        if (n < 1) {
            System.out.println("Calculates only for values >=1 !");
            return;
        }

        counter.set(1);
        finish = n;

        List<Function<Integer, String>> allFunctions = Arrays.asList(
                this::fizz,
                this::buzz,
                this::fizzbuzz
        );

        Thread ThreadA = new Thread(() -> processNumbers( allFunctions.get(0), 0));
        Thread ThreadB = new Thread(() -> processNumbers( allFunctions.get(1), 1));
        Thread ThreadC = new Thread(() -> processNumbers( allFunctions.get(2), 2) );
        Thread ThreadD = new Thread(() -> {

            while (true) {
                int counterValue = counter.get();
                if (counterValue > finish) {
                    break;
                }

                if (cache[0] == null || cache[1] ==null || cache[2]==null ) {
                    continue;
                }

                String specValue = "";
                for (String value : cache) {
                    if (!value.isEmpty()) {
                        specValue = value;
                        break;
                    }
                }

                number(specValue.isEmpty() ? (""+counterValue) : specValue);

                synchronized (cache) {
                  cache[0] = null;
                  cache[1] = null;
                  cache[2] = null;

                  counter.incrementAndGet();
                }
            }
        });

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
        int cursor = 0;
        while (true) {
            int counterValue = counter.get();
            if (counterValue > finish) {
                break;
            }

            if (cursor == counterValue) {
                continue;
            }

            cache[cacheIndex] = function.apply(counterValue);
            cursor = counterValue;
        }
    }

    private String fizz(int number){
        return number%3 == 0 && number%5 != 0 ? "fizz" : "";
    }

    private String buzz(int number){
        return number%5 == 0 && number%3 != 0 ? "buzz" : "";
    }

    private String fizzbuzz(int number) {
        return number%3 == 0 && number%5 == 0 ? "fizzbuzz" : "";
    }

    private void number(String number){
        System.out.print(number + (counter.get() == finish ? "" : delimiter));
    }
}
