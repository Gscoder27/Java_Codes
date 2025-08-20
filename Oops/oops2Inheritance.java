class person{
    public String name;
    public String address;
    public int age;
}
class Student extends person{
    public int marks;
    public String grade;

    Student(String name, String address, int age, int marks, String grade){
        this.name = name;
        this.address = address;
        this.age = age;
        this.marks = marks;
        this.grade = grade;
    }
    public void disp(){
        System.out.println("Name is : " + name);
        System.out.println("Address is : " + address);
        System.out.println("age is : " + age);
        System.out.println("marks is : " + marks);
        System.out.println("grade is : " + grade);
    }
}
public class oops2Inheritance {
    public static void main(String[] args) {
        Student obj = new Student("Pratik", "KOL", 40, 98, "A");
        obj.disp();
    }
}
