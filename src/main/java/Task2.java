public class Task2 {
    public static void main(String[] args) {

    FizzBuzzGenerator fb = new FizzBuzzGenerator();
        System.out.println("FizzBuzzGenerator for -5:");
        fb.generate(-5);
        System.out.println("------------------------------------");
        System.out.println("FizzBuzzGenerator for 0 :");
        fb.generate(0);
        System.out.println("------------------------------------");
        System.out.println("FizzBuzzGenerator for 1 :");
        fb.generate(1);
        System.out.println("\n" + "------------------------------------");
        System.out.println("FizzBuzzGenerator for 15 :");
        fb.generate(15);
        System.out.println("\n" + "------------------------------------");
        System.out.println("FizzBuzzGenerator for 31 :");
        fb.generate(31);
        System.out.println("\n" + "------------------------------------");
    }
}

