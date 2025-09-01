package LinkedList;

import java.util.ArrayList;
//  Implimentation of linked list

class Node {
    int val;
    Node next;

    Node(int val) {
        this.val = val;
        this.next = null;
    }
}

class SLL{
    Node head;
    Node tail;
    int size;
    void insertAtEnd(int val){
        Node temp = new Node(val);
        if(head == null) head=tail=temp;
        else{
            tail.next = temp;
            tail = temp;
        }
        size++;
    }

    void insertAtHead(int val){
        Node temp = new Node(val);
        if(head == null) head=tail=temp;
        else{
            temp.next = head;
            head = temp;
        }
        size++;
    }

    void insertAtIdx(int idx, int val) throws Error{
        if(idx==0){
            insertAtHead(val);
            return;
        }
        if(idx==size){
            insertAtEnd(val);
            return;
        }
        if(idx>size){
            throw new Error("Invalid Index");
        }

        Node temp = new Node(val);
        Node x = head;
        for(int i=0;i<idx-1;i++){
            x=x.next;
        } 
        temp.next=x.next;
        x.next = temp;
        size++;
    }

    int getMethod(int idx) throws Error{
        if(idx == size-1) return tail.val;
        if(idx>=size || idx<0) throw new Error("Invalid index");
        Node temp = head;
        for(int i=1;i<=idx;i++){
            temp=temp.next;
        }
        return temp.val;
    }

    public int tail(){
        Node temp = head;
        if(head==null){
            return head.val;
        }
        while(temp.next!=null){
            temp=temp.next;
        }
        return temp.val;
    } 

    void set(int idx, int val) throws Error{
        if(idx == size-1) tail.val = val;
        if(idx>=size || idx<0) throw new Error("Invalid Index");
        Node temp=head;
        for(int i=1;i<idx;i++){
            temp=temp.next;
        } 
        temp.val=val;
    }

    void delete(int idx) throws Error{
        if(idx == 0){
            head=head.next;
            size--;
            return;
        }
        if(head == null) throw new Error("List is Empty");
        if(idx<0 || idx>=size){
            throw new Error("Invalid index");
        }
        Node temp=head;
        for(int i=1;i<=idx-1;i++){
            temp=temp.next;
        }
        if(temp.next==tail) tail=temp;
        temp.next = temp.next.next;
        size--;
    }

    void display(){
        Node temp = head;
        while(temp!=null){
            System.out.print(temp.val+" ");
            temp = temp.next;
        }
        System.out.println();
    }

    void size(){
        System.out.println("Size of Linked List is :- " + size);
    }
}

public class LinkedListImplimentation {
    public static void main(String[] args) {
        SLL list = new SLL();
        System.out.println("Starting Linked List!!...");
        list.size();
        list.insertAtEnd(23);
        list.insertAtEnd(38);
        // list.display();
        // list.size();
        list.insertAtEnd(45);
        list.insertAtEnd(12);
        list.display();
        list.size();

        list.insertAtHead(10);
        list.insertAtHead(5);

        list.display();
        list.size();

        // list.insertAtIdx(9, 100);
        list.insertAtIdx(5, 100);
        list.display();
        list.size();

        System.out.println("The value at index 4 is :" + list.getMethod(4));
        System.out.println("The last element of LL is :- " + list.tail());
        list.delete(0);
        list.display();
        list.size();

        // list.delete(6);
        // list.display();
        // list.size();

        list.delete(5);
        list.display();
        list.size();

        list.set(2, 52);
        list.display();
        list.size();
    }
}
