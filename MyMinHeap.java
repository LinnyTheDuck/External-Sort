/// Stefenie Pickston 1506427
/// Holly Smallwood 1505405

public class MyMinHeap {
    public String heapArray[]; // initialising our array
    public int MAX;
    public int count;

    // dynamic constructor
    public MyMinHeap(int length) {
        MAX = length;
        heapArray = new String[MAX + 1];
        count = 0;
    }

    // fixed contructor
    public MyMinHeap() {
        MAX = 32;
        heapArray = new String[MAX + 1];
        count = 0;
    }

    //puts last output in index 0
    public void storeOutput(String s){
        heapArray[0] = s;
    }

    //get index 0
    public String retrieveStore(){
        return heapArray[0];
    }

    // inserts an item into the heap and upheaps it into heap order
    public void insert(String s) {
        if (count + 1 == heapArray.length) { // if the array is full resize
            //upHeap(s, count); //upheap minHeap
            return;
        } 
        else {
            int place = count + 1;
            heapArray[place] = s; // put in next avaliable spot
            count++; // increase size of heapArray
            upHeap(s, place);
        }
    }

    // swap top of heap with bottom, remove ref and downheap 
    public String remove() { 
        if (count == 0)
            return "HEAP IS EMPTY";
        else {
            String str = heapArray[1];
            swap(1, count); // swap first item with last item
            count--; // shorten heap by one
            downHeap(1);
            return str;
        }
    }

    // replace top of heap with the string parameter, then downheap
    public String replace(String s) {
        String str = heapArray[1];
        heapArray[1] = s;
        if (count == 0)
            count++;
        downHeap(1);
        return str;
    }

    // view the top item of the heap
    public String peek() {
        if (count == 0) { // if the size of the queue is empty
            //System.err.println("WARINING! THE HEAP IS EMPTY!");
            return null;
        } else
            return heapArray[1]; // returns the root of the queue (smallest item)
    }

    // load the string array into unsorted heap
    public void load(String[] stringArray) {
        for (int i = 0; i < stringArray.length; i++) {
            if(stringArray[i] != null){ // so nulls dont cause exceptions
                heapArray[i + 1] = stringArray[i];
                count++;
            }
        }
        
    }

    // heap sorts the array
    public void reHeap() {
        int i = count;
        while (i > 1) {
            if (i % 2 == 0)
                i = i / 2;

            downHeap(i);
            i--;
        }
        downHeap(1); // check heap root
    }

    // puts the last item in the array/heap into heap order
    private void upHeap(String s, int i) {
        int j = 1;
        if(i/2 > 1)
            j = i/2;

        if (heapArray[j].compareTo(s) > 0 && i > 1) {
            heapArray[i] = heapArray[i/2];
            heapArray[i/2] = s; // while we have parent and if smaller than parent then swap
            upHeap(s, i/2);
        }
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
                if (heapArray[leftChild].compareTo(heapArray[parent]) < 0) {
                    smallest = leftChild;
                    swap(smallest, parent);
                }
                return;
            } else {// both right child and left child
                    // if neither of the children are smaller
                if (heapArray[leftChild].compareTo(heapArray[parent]) > 0
                        && heapArray[rightChild].compareTo(heapArray[parent]) > 0) {
                } else {
                    // find smallest child
                    if (heapArray[leftChild].compareTo(heapArray[rightChild]) < 0) {
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
        String tempOne = heapArray[j];
        String tempTwo = heapArray[k];
        heapArray[j] = tempTwo;
        heapArray[k] = tempOne;
    }

    //////////DEBUGGING TOOLS//////////

    // create new array and copy everything over
    public void reSize() {
        String temp[] = new String[heapArray.length + heapArray.length / 2];
        for (int i = 1; i < heapArray.length; i++) {
            temp[i] = heapArray[i];
        }
        heapArray = temp;
    }

    // return true if nothing in heap, false otherwise
    public boolean isEmpty() { 
        if (count == 0)
            return true;
        return false;
    }

    // remove everything from heap
    public void clear(){  
        count=0; 
    }

    // return count of how many items in heap
    public int size(){ 
        return count; 
    }

    // print out all the ordered items in the heap
    public void print() {
        for (int i = 0; i <= count; i++) {
            System.out.println(heapArray[i]);
        }
    }
}
