package binomialheap;

public class Node {

  int key;
  Node parent;
  int degree;
  Node child;
  Node sibling;

  public Node(int key, Node parent, int degree, Node child, Node sibling) {
    super();
    this.key = key;
    this.parent = parent;
    this.degree = degree;
    this.child = child; 
    this.sibling = sibling;
  }
}
