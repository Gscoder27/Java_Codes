class Account {
    // Data Security
    private double balance;
    // method :: public
    public double getBalance(double balance){
        //  withdrawign the money
        // boolean result = validate("Sachin", "@in34"); // return else
        boolean result = validate("Sachin", "Sach@in34"); 
        if(result == true){
            this.balance = this.balance - balance;
            return balance; 
        }
        else{
            System.out.println("Invalid username/password try again...");
            return 0.0;
        }
        
    }
    // methods :: public
    public void setBalance(double balance){
        // depositing the money
        boolean result = validate("Sachin", "Sach@in34");
        // perform authentication
        if(result == true){
            // deposit money
            this.balance = this.balance + balance;
            System.out.println("Crediated to account successfully!");
        }
        else{
            // throw a meaningfull message 
            System.out.println("Invalid username/password try again...");
        }
    }
    // methods :: private
    private boolean validate(String userName, String password){
        // logic for authentication
        return userName.equalsIgnoreCase("Sachin") && password.equals("Sach@in34"); // return a boolen value

        // return true;
    }
    
}

public class oops1TestApp {
    public static void main(String[] args) {
        Account obj = new Account();
        // System.out.println(obj.balance); error 
        obj.setBalance(1000.0);
        double balance = obj.getBalance(500.0);
        System.out.println("Withdrawing :: " + balance + " Amount ");
    }
}
