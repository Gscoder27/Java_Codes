class Student{
    //  Data Security
    private int rollNo;
    private String name;
    private String address;

    //  Setter Methods
    public void setRollNo(int rollNo){
        this.rollNo = rollNo;
    }

    public void setName(String Name){
        this.name = Name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    // getter Methods
    public int getRollNo(){
        return rollNo;
    }
    
    public String getName()
    {
        return name;
    }

    public String getAddress(){
        return address;
    }
}

public class oops1StudentApp{
    public static void main(String[] args) {
        Student s1 = new Student();

        // calling setter methods
        s1.setAddress("256-AD/3 F-Block SaltLake");
        s1.setName("Harshdeep");
        s1.setRollNo(56);

        // calling getter methods
        System.out.println("RollNo is :: " + s1.getRollNo());
        System.out.println("Name is :: " + s1.getName());
        System.out.println("Address is :: "+ s1.getAddress());
    }
}
