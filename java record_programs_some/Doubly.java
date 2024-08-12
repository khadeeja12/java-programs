class Node {
    int data;
    Node prev;
    Node next;

    public Node(int data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

class DoublyLinkedList {
    Node head;

    public void insert(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.prev = temp;
        }
    }

    public void delete(int key) {
        Node current = head;
        while (current != null && current.data != key) {
            current = current.next;
        }
        if (current == null) {
            System.out.println("Element not found in the list.");
            return;
        }
        if (current == head) {
            head = current.next;
        }
        if (current.prev != null) {
            current.prev.next = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        }
        System.out.println("Element " + key + " deleted from the list.");
    }

    public void display() {
        Node temp = head;
        System.out.print("Doubly Linked List: ");
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }
}

public class Doubly {
    public static void main(String[] args) {
        DoublyLinkedList dll = new DoublyLinkedList();
        
        // Insert elements into the doubly linked list
        dll.insert(1);
        dll.insert(2);
        dll.insert(3);
        dll.insert(4);
        dll.insert(5);

        // Display the contents of the list before deletion
        dll.display();

        // Delete an element (e.g., delete 3 from the list)
        dll.delete(3);

        // Display the contents of the list after deletion
        dll.display();
    }
}
