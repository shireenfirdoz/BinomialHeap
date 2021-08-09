package binomialheap;

public class BinomialHeap {

  Node head;
  Node sentinel;

  public BinomialHeap() {
    sentinel = new Node(Integer.MIN_VALUE, null, -1, null, null);
    head = sentinel;
  }

  public BinomialHeap makeHeap() {
    BinomialHeap heap = new BinomialHeap();
    return heap;
  }

  public void insert(int key) {
    BinomialHeap tempHeap = makeHeap();
    Node x = new Node(key, sentinel, 0, sentinel, sentinel);
    tempHeap.head = x;
    BinomialHeap heap = union(this, tempHeap);
    head = heap.head;

  }

  public Node minimum() {
    Node minNode = sentinel;
    Node temp = head;
    int min = Integer.MAX_VALUE;
    while (temp != sentinel) {
      if (temp.key < min) {
        min = temp.key;
        minNode = temp;
      } else {
        temp = temp.sibling;
      }
    }

    return minNode;
  }

  public Node reverseNode(Node node) {

    Node temp = node;
    Node prevTemp = sentinel;
    Node sibling;

    while (temp != sentinel) {
      sibling = temp.sibling;
      temp.sibling = prevTemp;
      prevTemp = temp;
      temp = sibling;

    }
    return prevTemp;
  }

  public int extractMin() {

    if (head == sentinel)
      return -1;

    Node node = this.head;
    Node prevNode = sentinel;
    Node minNode = this.minimum();

    while (node.key != minNode.key) {
      prevNode = node;
      node = node.sibling;
    }

    if (prevNode == sentinel) {
      head = node.sibling;
    } else {
      prevNode.sibling = node.sibling;
    }

    node = node.child;
    Node dummyNode = node;

    while (node != sentinel) {
      node.parent = null;
      node = node.sibling;
    }

    // reverse the linked list

    Node rev = reverseNode(dummyNode);

    // makeHeap h'

    BinomialHeap heapTemp = makeHeap();

    heapTemp.head = rev;

    // union
    BinomialHeap heapTemp1 = union(this, heapTemp);
    head = heapTemp1.head;

    return minNode.key;
  }

  private void binomialLink(Node nodeA, Node nodeB) {
    if (nodeA == sentinel || nodeB == sentinel) {
      return;
    }

    nodeA.parent = nodeB;
    nodeA.sibling = nodeB.child;
    nodeB.child = nodeA;
    nodeB.degree = nodeB.degree + 1;

  }

  private Node merge(Node heapA, Node heapB) {
    Node thisHeap = heapA;
    Node bHeap = heapB;
    Node newHeap = null;

    if (thisHeap.degree < bHeap.degree) {
      newHeap = thisHeap;
      thisHeap = thisHeap.sibling;
    } else {
      newHeap = bHeap;
      bHeap = bHeap.sibling;
    }
    heapA = newHeap;

    while (thisHeap != sentinel && bHeap != sentinel) {
      if (thisHeap.degree < bHeap.degree) {
        newHeap.sibling = thisHeap;
        thisHeap = thisHeap.sibling;
      } else {
        newHeap.sibling = bHeap;
        bHeap = bHeap.sibling;
      }
      newHeap = newHeap.sibling;
    }
    if (bHeap == sentinel) {
      newHeap.sibling = thisHeap;
    } else {
      newHeap.sibling = bHeap;
    }

    return heapA;
  }

  public Node merge(BinomialHeap heapA, BinomialHeap heapB) {
    if (heapB.head == sentinel) {
      return heapA.head;
    }

    if (heapA.head == sentinel) {
      return heapB.head;
    }

    return merge(heapA.head, heapB.head);

  }

  /**
   * Method to consolidate the heaps.
   * @param heapA
   * @param heapB
   * @return
   */
  public BinomialHeap union(BinomialHeap heapA, BinomialHeap heapB) {
    BinomialHeap heap = makeHeap();
    heap.head = merge(heapA, heapB);
    if (heap.head == sentinel)
      return heap;

    Node prevX = sentinel;
    Node x = heap.head;
    Node nextX = x.sibling;
    while (nextX != sentinel) {
      if ((x.degree != nextX.degree)
          || ((nextX.sibling != sentinel) && (nextX.sibling.degree == x.degree))) {
        prevX = x;
        x = nextX;
      } else {
        if (x.key <= nextX.key) {
          x.sibling = nextX.sibling;
          //binomialLink(nextX, x);
          nextX.parent = x;
          nextX.sibling = x.child;
          x.child = nextX;
          x.degree++;
        } else {
          if (prevX == sentinel) {
            heap.head = nextX;
          } else {
            prevX.sibling = nextX;
          }
          x.parent = nextX;
          x.sibling = nextX.child;
          nextX.child = x;
          nextX.degree++;
          x = nextX;
        }
      }
      nextX = x.sibling;
    }

    return heap;

  }

  /* Method to find node with key value */
  public Node findANodeWithKey(Node head, int value) {
    Node temp = head, node = sentinel;

    while (temp != sentinel) {
      if (temp.key == value) {
        node = temp;
        break;
      }
      if (temp.child == sentinel)
        temp = temp.sibling;
      else {
        node = findANodeWithKey(temp.child, value);
        if (node == null)
          temp = temp.sibling;
        else
          break;
      }
    }

    return node;
  }

  public void decreaseKey(int oldKey, int newKey) {

    if (oldKey <= newKey) {
      System.out.println("Cannot decrease key eith newkey is greater or equal to old key");
      return;
    }

    Node temp = findANodeWithKey(this.head, oldKey);
    if (temp == sentinel)
      return;
    temp.key = newKey;
    Node tempParent = temp.parent;

    while ((tempParent != null) && (temp.key < tempParent.key)) {
      int z = temp.key;
      temp.key = tempParent.key;
      tempParent.key = z;

      temp = tempParent;
      tempParent = tempParent.parent;
    }
  }

  public void delete(int key) {

    if ((this.head != sentinel) && (findANodeWithKey(this.head, key) != sentinel)) {
      Node min = this.minimum();
      if (min != sentinel) {
        if (min.key != key) {
          decreaseKey(key, (min.key - 1));
        }
        extractMin();
      } else {
        System.out.println("cannot find the min");
      }
    } else {
      System.out.println("head is null");
    }

  }

  public void printHeap() {
    System.out.print("\nHeap : ");
    printHeap(this.head);
    System.out.println("\n");
  }

  private void printHeap(Node node) {
    if (node != sentinel) {
      printHeap(node.child);
      System.out.println("key -->" + node.key + "   degree -->" + node.degree + "   parent -->"
          + (node.parent == null ? Integer.MIN_VALUE : node.parent.key) + "   child -->"
          + (node.child == null ? Integer.MIN_VALUE : node.child.key) + "   sibling -->"
          + (node.sibling == null ? Integer.MIN_VALUE : node.sibling.key));
      printHeap(node.sibling);
    }
  }

}
