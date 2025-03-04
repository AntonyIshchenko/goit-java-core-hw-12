public class Task1 {
    public static void main(String[] args) throws InterruptedException {

        Thread firstThread = new Thread(()->{
            int count = 0;
            int stopCount = 10000;

            while (count <= stopCount ){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                System.out.println(count);
            }
        });

        Thread secondThread = new Thread(()->{
            int count = 0;
            int stopCount = 2000;

            while (count <= stopCount) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Past 5 seconds");
                count++;
            }
        });

        firstThread.start();
        secondThread.start();
    }
}
