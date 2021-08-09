package binomialheap;

public class Driver {

  public Driver() {

  }

  public static void main(String[] args) {
    BinomialHeap heap = new BinomialHeap();
    heap.insert(20);
    heap.insert(30);
    heap.insert(40);
    heap.insert(50);
    heap.insert(60);
    heap.insert(5);
    heap.insert(15);


    System.out.println(heap);
    heap.printHeap();


    int min = heap.extractMin();
    System.out.println("extract min---"+min );
    heap.printHeap();

    heap.decreaseKey(60, 25);

    System.out.println("decrease---");

    heap.printHeap();

    heap.extractMin();

    System.out.println("extractMin---15 " );

    heap.printHeap();
    
    heap.delete(25);
    
    heap.printHeap();
    

  }

}
