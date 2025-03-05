public class Task2 {
    public static void main(String[] args) {

    FizzBuzzGenerator fb = new FizzBuzzGenerator();
        System.out.println("FizzBuzzGenerator for -5 = " + fb.generate(-5));
        System.out.println("FizzBuzzGenerator for 0 = " + fb.generate(0));
        System.out.println("FizzBuzzGenerator for 1 = " + fb.generate(1));
        System.out.println("FizzBuzzGenerator for 30 = " + fb.generate(30));
        System.out.println("FizzBuzzGenerator for 23 = " + fb.generate(23));
    }
}

