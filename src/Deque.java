import tester.*;
import java.util.function.*;

// ANode abstract class 
abstract class ANode<T> {
  ANode<T> next; 
  ANode<T> prev;
  
  ANode() {
    this.next = null;
    this.prev = null;
  }
  
  ANode(ANode<T> next, ANode<T> prev) {
    this.next = next;
    this.prev = prev;
  }
  
  // returns the number of nodes in a list
  public abstract int size();
  
  // method for addAtHead and addAtTail in deque class
  public void insert(T value, ANode<T> after, ANode<T> prev) {
    new Node<T>(value,after,prev);
  }

  // removes first node from deque
  public T removeFromHead() {
    return this.removeHelper();
  }
  
  // removes last node from deque
  public T removeFromTail() {
    return this.removeHelper();
  }
  
  // remove helper for removeFromTail and Head
  public abstract T removeHelper();
  
  // returns ANode that satisfies given predicate
  public abstract ANode<T> find(Predicate<T> pred);
  
  // removes given node from deque
  public void removeNode(ANode<T> node) {
    // this method should not do anything since it refers to sentinel
    // the removeNode method is overridden in the node class
  }
  
 
}

// Sentinel class that extends ANode class
class Sentinel<T> extends ANode<T> {

  Sentinel() {
    super();
    this.next = this;
    this.prev = this;
  }
  
  // returns 0 since size of any sentinel node is 0
  public int size() {
    return 0; 
  }
  
  public ANode<T> find(Predicate<T> pred) {
    return this;
  }
  
  public T removeHelper() {
    throw new RuntimeException("Can't remove head or tail from empty list!");
  }
  
}

// Node class that extends ANode class
class Node<T> extends ANode<T> {
  T data;
  
  Node(T data) {
    this.data = data;
  } 
  
  Node(T data, ANode<T> next, ANode<T> prev) {
    super(next, prev);
    if (next == null || prev == null) {
      throw new IllegalArgumentException("Node is null!");
    }   
    this.data = data;
    next.prev = this;
    prev.next = this;   
  } 
  
  // returns size of node
  public int size() {
    return 1 + next.size();
  }
  
  // returns the data that is being removed at the front and end of of list
  public T removeHelper() {
    this.next.prev = this.prev;
    this.prev.next = this.next;
    return this.data;
  }
  
  // returns ANode that satisfies the given predicate
  public ANode<T> find(Predicate<T> pred) {
    if (pred.test(this.data)) {
      return this;
    }
    else {
      return this.next.find(pred);
    }        
  }
  
  // removes the given node in the deque by searching through until the given is found
  @Override
  public void removeNode(ANode<T> node) {
    if (this == node) {
      node.removeHelper();
    }
    else {
      this.next.removeNode(node);
    }
  }
  
}

// Deque class
class Deque<T> {
  Sentinel<T> header;
  
  Deque() {
    this.header = new Sentinel<T>();
  }
  
  Deque(Sentinel<T> header) {
    this.header = header;
  }
  
  // returns size of deque
  public int size() {
    return header.next.size();
  }
  
  // adds a new Node with the value T at the front of list
  public void addAtHead(T value) {
    this.header.next.insert(value, this.header.next, this.header);
  }
  
  // adds a new Node with the value T at the end of list
  public void addAtTail(T value) {
    this.header.prev.insert(value,this.header,this.header.prev);
  }
  
  // calls the header's (sentinel) next referenced value's removeFromHead method
  public T removeFromHead() {
    return this.header.next.removeHelper();
  }
  
  // calls the header's (sentinel) previous referenced value's removeFromTail method
  public T removeFromTail() {
    return this.header.prev.removeHelper();
  }
  
  // calls the header's next value on the find method with the given predicate
  public ANode<T> find(Predicate<T> pred) {
    return this.header.next.find(pred);
  }
  
  // removes the given node in the deque
  public void removeNode(ANode<T> node) {
    this.header.next.removeNode(node);
  }

}

// Equalsnum class that determines if two integers are equal
class Equalsnum implements Predicate<Integer> {
  int num;
  
  Equalsnum(int num) {
    this.num = num;
  }
  
  // determines if two integers are equal
  public boolean test(Integer t) {
    return t == num;  
  }
  
}

// GreaterString class that determines if given string is greater than the one being compared
class GreaterString implements Predicate<String> {
  String str;
  
  GreaterString(String str) {
    this.str = str;
  }
  
  // determines if the given string is greater than the one being compared
  public boolean test(String t) {
    return str.compareTo(t) < 0;  
  }
  
}

// Examples class
class ExamplesDeque {
  ExamplesDeque() {}
  
  Deque<String> empty;
  Deque<String> deque1;
  Deque<Integer> deque2;
  Deque<String> deque3;
  Node<String> node1;
  Node<String> node2;
  Node<String> node3;
  Node<String> node4;
  Node<Integer> node5;
  Node<Integer> node6;
  Node<Integer> node7;
  Node<Integer> node8;
  Node<String> node9;
  Node<String> node10;
  Node<String> node11;
  Node<String> node12;
  Node<String> node13;
  
  Sentinel<String> sentinel1; 
  Sentinel<Integer> sentinel2;
  Sentinel<String> sentinel3;
  
  Predicate<Integer> predInt;
  Predicate<String> predString;

 
  void initData() {
    this.empty = new Deque<String>();
    
    sentinel1 = new Sentinel<String>();
    this.deque1 = new Deque<String>(sentinel1);
    this.node1 = new Node<String>("abc",sentinel1, sentinel1);
    this.node2 = new Node<String>("bcd",sentinel1, node1);
    this.node3 = new Node<String>("cde",sentinel1, node2);
    this.node4 = new Node<String>("def",sentinel1, node3);
    
    sentinel2 = new Sentinel<Integer>();
    this.deque2 = new Deque<Integer>(sentinel2);
    this.node5 = new Node<Integer>(4,sentinel2, sentinel2);
    this.node6 = new Node<Integer>(2,sentinel2, node5);
    this.node7 = new Node<Integer>(3,sentinel2, node6);
    this.node8 = new Node<Integer>(1,sentinel2, node7);
    
    sentinel3 = new Sentinel<String>();
    this.deque3 = new Deque<String>(sentinel3);
    this.node9 = new Node<String>("a",sentinel3, sentinel3);
    this.node10 = new Node<String>("b",sentinel3, node9);
    this.node11 = new Node<String>("c",sentinel3, node10);
    this.node12 = new Node<String>("d",sentinel3, node11);
    this.node13 = new Node<String>("e",sentinel3, node12);
    
    predInt = new Equalsnum(2);
    predString = new GreaterString("c");
  }
  
  // checks for illegalargumentexceptions in node constructor
  boolean testBadNode(Tester t) {
    this.initData();
    
    return t.checkConstructorException(new IllegalArgumentException("Node is null!"),
        "Node", "hi", null, null)
        && t.checkConstructorException(new IllegalArgumentException("Node is null!"),
            "Node", "hi", this.node3, null)
        && t.checkConstructorException(new IllegalArgumentException("Node is null!"),
            "Node", "hi", null, this.node10);
    
  }
  
  // checks for size of deque
  boolean testSize(Tester t) {
    this.initData();
    return t.checkExpect(deque1.size(), 4)
        && t.checkExpect(empty.size(), 0)
        && t.checkExpect(deque3.size(), 5);
  }
  
  // checks for addAtHead method on deque
  boolean testaddAtHead(Tester t) {
    this.initData();
    
    empty.addAtHead("e");
    deque3.addAtHead("a");
    deque2.addAtHead(0);
    deque1.addAtHead("bca");
    
    return t.checkExpect(empty.header.next,
        new Node<String>("e",empty.header.next,empty.header.prev))
        && t.checkExpect(sentinel3.next, new Node<String>("a", node9, sentinel3))
        && t.checkExpect(sentinel2.next, new Node<Integer>(0, node5, sentinel2))
        && t.checkExpect(sentinel1.next, new Node<String>("bca", node1, sentinel1));
      
  }
  
  // checks for addAtTail method for deque
  boolean testaddAtTail(Tester t) {
    this.initData();
    
    empty.addAtTail("hi");
    deque3.addAtTail("f");
    deque2.addAtTail(7);
    deque1.addAtTail("fgh");
    
    return t.checkExpect(empty.header.prev,
        new Node<String>("hi", empty.header.next, empty.header.prev))
        && t.checkExpect(sentinel3.prev, new Node<String>("f", sentinel3, node13))
        && t.checkExpect(sentinel2.prev, new Node<Integer>(7, sentinel2, node8))
        && t.checkExpect(sentinel1.prev, new Node<String>("fgh", sentinel1, node4));
    
  }
  
  // checks for removeFromHead method for deque
  boolean testRemoveFromhead(Tester t) {
    this.initData();
    
    
    return t.checkException(new RuntimeException("Can't remove head or tail from empty list!"),
        this.empty, "removeFromHead")
        && t.checkExpect(deque1.removeFromHead(), "abc")
        && t.checkExpect(deque2.removeFromHead(), 4)
        && t.checkExpect(deque3.removeFromHead(), "a");
           
  }
  
  // checks for removeFromTail method for deque
  boolean testRemoveFromTail(Tester t) {
    this.initData();
    
    return t.checkException(new RuntimeException("Can't remove head or tail from empty list!"),
        this.empty, "removeFromTail")
        && t.checkExpect(deque1.removeFromTail(), "def")
        && t.checkExpect(deque2.removeFromTail(), 1)
        && t.checkExpect(deque3.removeFromTail(), "e");
  }
  
  // checks for find method for deque
  boolean testFind(Tester t) {
    this.initData();
    
    return t.checkExpect(deque2.find(predInt), node6)
        && t.checkExpect(deque3.find(predString), node12)
        && t.checkExpect(deque1.find(predString), node3);

  }
  
  // checks for remove method for deque
  boolean testRemove(Tester t) {
    this.initData();
    
    deque1.removeNode(node3);
    deque2.removeNode(node8);
    deque3.removeNode(node9);
    
    return t.checkExpect(node4.prev, node2)
        && t.checkExpect(node2.next, node4)
        && t.checkExpect(node7.next, sentinel2)
        && t.checkExpect(node10.prev, sentinel3);
  }
  
  // checks for removeHelper method for deque
  boolean testRemoveHelper(Tester t) {
    this.initData();
    
    return t.checkException(new RuntimeException("Can't remove head or tail from empty list!"),
        sentinel1, "removeHelper")
        && t.checkExpect(node1.removeHelper(), "abc")
        && t.checkExpect(node5.removeHelper(), 4)
        && t.checkExpect(node9.removeHelper(), "a");
  }
  
  // checks for insert method for deque
  boolean testInsert(Tester t) {
    this.initData();
    
    node1.insert("abc", node2, sentinel1);
    node8.insert(1, sentinel2, node7);
    node9.insert("a", node10, sentinel3);
    
    return t.checkExpect(node1.next, node2)
        && t.checkExpect(node8.prev, node7)
        && t.checkExpect(node9.next, node10);
    
  }
  
}





