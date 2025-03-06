import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

public class FizzBuzzGenerator{


    public String generate(int n)  {
        if (n < 1) {
            return "Calculates only for values >=1 !";
        }

        List<Function<Integer, String>> allFunctions = Arrays.asList(
                this::fizz,
                this::buzz,
                this::fizzbuzz,
                this::number
        );

        List<List<String>> allLists = Arrays.asList(
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
        );

        Thread ThreadA = new Thread(() -> processNumbers(allLists.get(0), n, allFunctions.get(0)));
        Thread ThreadB = new Thread(() -> processNumbers(allLists.get(1), n, allFunctions.get(1)));
        Thread ThreadC = new Thread(() -> processNumbers(allLists.get(2), n, allFunctions.get(2)) );
        Thread ThreadD = new Thread(() -> processNumbers(allLists.get(3), n, allFunctions.get(3)) );

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

        StringJoiner joiner = new StringJoiner(", ");
        for (int i=0; i < n; i++ ){
            for (int j=3; j>=0; j--){
                if (allLists.get(j).get(i) != "") {
                    joiner.add(allLists.get(j).get(i));
                    break;
                }
            }
        }

        return joiner.toString();
    }

    private void processNumbers(List<String> list, int maxNumber,Function<Integer, String> function) {
            for (int i = 1; i<= maxNumber; i++ ) {
                list.add(function.apply(i));
            }
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
