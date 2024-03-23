import java.util.Scanner;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class CircularQueue {
    Node front, rear;
    int count;

    // Constructor to initialize the circular queue
    CircularQueue() {
        this.front = this.rear = null;
        this.count = 0;
    }

    // Method to insert a node at the end of the queue
    void insert(int value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            front = rear = newNode;
            rear.next = front;
            count++;
        } else {
            rear.next = newNode;
            rear = newNode;
            rear.next = front;
            count++;
            if (count == 2) {
                popTwo();
            }
        }
    }

    // Method to pop out two elements from the front of the queue
    void popTwo() {
        Node temp = front;
        System.out.println("Popping out two elements: ");
        for (int i = 0; i < 2; i++) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
        front = temp;
        rear.next = front;
        count -= 2;
    }

    // Method to delete a node from the front of the queue
    void delete() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Cannot delete.");
            return;
        }
        if (front == rear) {
            front = rear = null;
        } else {
            front = front.next;
            rear.next = front;
            count--;
        }
    }

    // Method to check if the queue is empty
    boolean isEmpty() {
        return (front == null && rear == null);
    }

    // Method to print all the data from nodes within the queue
    void traverse() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        Node temp = front;
        do {
            System.out.print(temp.data + " ");
            temp = temp.next;
        } while (temp != front);
        System.out.println();
    }

    // Method to search for a value in the queue
    void search(int value) {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        Node temp = front;
        int position = 0;
        do {
            if (temp.data == value) {
                System.out.println("Found " + value + " at position " + position);
                return;
            }
            temp = temp.next;
            position++;
        } while (temp != front);
        System.out.println(value + " not found in the queue.");
    }
}

public class linkedlist{
    public static void main(String[] args) {
        CircularQueue circularQueue = new CircularQueue();
        Scanner scanner = new Scanner(System.in);
        char choice;
        while (true){
            System.out.print("insert into the queue: ");
            int value = scanner.nextInt();
            circularQueue.insert(value);
            circularQueue.traverse();
        } 

        
        
    }
}
