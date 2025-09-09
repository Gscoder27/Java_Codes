package Threads;

import java.util.*;

class Counter implements Runnable {
    private int cnt = 0;

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            // cnt++;
            try{  // we use cz thread could be intrupted while sleeping
                Thread.sleep(1000); // sleep for 1000 milliseconds
                cnt++;
            }
            catch(InterruptedException e){
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        // System.out.println("Final count: " + cnt);

            if (i==10){
                System.out.println("Times up");
                System.exit(0); // As soon as we enter the name the   counter stops or the time stops i.e. thread being ended or else if we don't enter the name in 10 seconds the program ends automatically
            } 
        }
    }
}

public class Demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Counter obj = new Counter();

        Thread t = new Thread(obj);
        t.setDaemon(true); // to make it a daemon thread - once the main thread is done executing the daemon thread stops/ exits the program and doesn't waits for 10s complete once the name is entered before 10 seconds  
        t.start();


        System.out.println("You have 10 seconds to enter your name: ");
        System.out.print("Enter your name: ");
        String nm = sc.nextLine();
        System.out.println("Hello, " + nm + "!");
    }
}