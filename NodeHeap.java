/// Stefenie Pickston 1506427
/// Holly Smallwood 1505405

public class NodeHeap {
    public Node heapArray[]; // initialising our array
    public int MAX;
    public static int count;

    // constructor
    public NodeHeap(int length) {
        MAX = length + 1;
        heapArray = new Node[MAX];
        count = 0;
    }

    // view the top item of the heap
    public Node peek() {
        if (count == 0) { // if the size of the queue is empty
            //System.err.println("WARINING! THE HEAP IS EMPTY!");
            return null;
        } else
            return heapArray[1]; // returns the root of the queue (smallest item)
    }

    // load the string array into unsorted heap
    public void load(Node[] nodeArray) {
        count = 0;
        for (int i = 0; i < nodeArray.length; i++) {
            if(nodeArray[i] != null && nodeArray[i].value != null){ // so nulls dont cause exceptions
                heapArray[i + 1] = nodeArray[i];
                count++;
            }
            else
                heapArray[i + 1] = null;
        }
    }

    // replace top of heap with the string parameter, then downheap
    public void replace(Node s) { 
        heapArray[1] = s;
        if (count == 0)
            count++;
        downHeap(1);
    }

    // swap top of heap with bottom, remove ref and downheap 
    public void remove() { 
        if (count == 0)
            return;
        else {
            swap(1, count); // swap first item with last item
            count--; // shorten heap by one
            downHeap(1);
        }
    }

    // heap sorts the array
    public void reHeap() {
        int i = count;
        //System.out.println(count);
        while (i > 1) {
            if (i % 2 == 0)
                i = i / 2;

            downHeap(i);
            i--;
        }
        downHeap(1); // check heap root
    }

    // downheaps the top item in the heap
    private void downHeap(int i) { 

        int parent = i; // count position for parent
        int smallest = 0;
        int leftChild = parent * 2;
        int rightChild = (2 * parent) + 1;

        while (leftChild <= count && rightChild <= count + 1 ) { // checks if we have a child
            // if there is a left child but no right 
            if (leftChild <= count && rightChild == count + 1) {
                //System.out.println(leftChild + " " + rightChild + " " + count);
                if (heapArray[leftChild].value.compareTo(heapArray[parent].value) < 0) {
                    smallest = leftChild;
                    swap(smallest, parent);
                }
                return;
            } else {// both right child and left child
                    // if neither of the children are smaller
                if (heapArray[leftChild].value.compareTo(heapArray[parent].value) > 0
                        && heapArray[rightChild].value.compareTo(heapArray[parent].value) > 0) {
                } else {
                    // find smallest child
                    if (heapArray[leftChild].value.compareTo(heapArray[rightChild].value) < 0) {
                        smallest = leftChild;
                        swap(smallest, parent);
                    } else {
                        smallest = rightChild;
                        swap(smallest, parent);
                    }
                }
            }
            parent++; // increases count position of parents;
            leftChild = parent * 2;
            rightChild = (parent * 2) + 1;
        }
    }

    // swap values [j] and [k] in the heap
    public void swap(int j, int k) {
        Node tempOne = heapArray[j];
        Node tempTwo = heapArray[k];
        heapArray[j] = tempTwo;
        heapArray[k] = tempOne;
    }
}