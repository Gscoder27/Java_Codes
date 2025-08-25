package GCD;

public class GCDofNum {

    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        // return gcd(b, a % b);
        else return gcd(b, Math.abs(a - b));
    }

    public static void main(String[] args) {
        int num1 = 56;
        int num2 = 98;
        System.out.println("GCD of " + num1 + " and " + num2 + " is: " + gcd(num1, num2));
    }       
}
