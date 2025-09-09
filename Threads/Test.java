package Threads;

class Counter{
    int cnt=0;
    public void increment(){
        cnt++;
    }
}

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Counter counter=new Counter();

        Thread t1=new Thread(new Runnable() {
            public void run() {
                for(int i=0;i<100;i++){
                    counter.increment();
                }
            }
        });

        Thread t2=new Thread(new Runnable() {
            public void run() {
                for(int i=0;i<100;i++){
                    counter.increment();
                }
            }
        });

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println(counter.cnt);
    }
}
