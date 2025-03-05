import java.util.StringJoiner;

public class FizzBuzzGenerator{
    private int maxNumber;
    private int currentNumber;
    private final String[] cache = new String[4];
    private final Object monitor = new Object();

    public String generate(int n){
        if (n < 1) {
            return "Calculates only for values >=1 !";
        }

        this.maxNumber = n;
        this.currentNumber = 1;

        Thread ThreadA = new Thread(() -> {
            synchronized (monitor) {
                while (currentNumber <= maxNumber) {
                    cache[0] = fizz(currentNumber);
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } );

        Thread ThreadB = new Thread(() -> {
            synchronized (monitor) {
                while (currentNumber <= maxNumber) {
                    cache[1] = buzz(currentNumber);
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } );

        Thread ThreadC = new Thread(() -> {
            synchronized (monitor) {
                while (currentNumber <= maxNumber) {
                    cache[2] = fizzbuzz(currentNumber);
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } );

        Thread ThreadD = new Thread(() -> {
            synchronized (monitor) {
                while (currentNumber <= maxNumber) {
                    cache[3] = number(currentNumber);
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } );

        boolean isStarted = false;
        StringJoiner joiner = new StringJoiner(", ");

        while (currentNumber <= maxNumber){
            if (!isStarted) {
                ThreadA.start();
                ThreadB.start();
                ThreadC.start();
                ThreadD.start();
                isStarted = true;
            }

            while (true) {
              if (cache[0] != null && cache[1] != null && cache[2] != null && cache[3] !=null) {

                  for (int i=3; i>=0; i--){
                      if (cache[i] != "") {
                          joiner.add(cache[i]);
                          break;
                      }
                  }

                  for (int i=0; i<4; i++){
                      cache[i] = null;
                  }

                  currentNumber++;

                  synchronized (monitor) {
                      monitor.notifyAll();
                  }

                  break;
              }
            }
        }

        return joiner.toString();
    }

    private String fizz(int number){
        return (number%3 == 0) ? "fizz" : "";
    }

    private String buzz(int number){
        return (number%5 == 0) ? "buzz" : "";
    }

    private String fizzbuzz(int number){
        return (number%3 == 0 && number%5 == 0 ) ? "fizzbuzz" : "";
    }

    private String number(int number){
        return (number%3 != 0 && number%5 != 0 ) ? ""+number : "";
    }
}
