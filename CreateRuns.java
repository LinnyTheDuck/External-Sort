/// Stefenie Pickston 1506427
/// Holly Smallwood 1505405

import java.io.*;

public class CreateRuns {

    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) {
        int minHeapCapacity;
        if (args.length != 1) { // gets size
            minHeapCapacity = 32; // default size if not specified is 32
        } else if (Integer.parseInt(args[0]) < 1) {
            minHeapCapacity = 32;
        } else {
            minHeapCapacity = Integer.parseInt(args[0]);
        }

        MyMinHeap minHeap = new MyMinHeap(minHeapCapacity);

        try {
            // br = new BufferedReader(new FileReader("info2.txt")); // for debugging

            int finalCap = 0; // counting the final capacity
            String str; // string to read and checked
            Boolean runs = true, flagEnd = false, flagStart = false; // flags for loops
            String lineArray[] = new String[minHeapCapacity + 1];

            for(int i = minHeap.count; i < minHeapCapacity; i++){ // insert everything into array
                str = br.readLine();
                lineArray[i] = str;
            }

            minHeap.load(lineArray); // load strings into minHeap
            minHeap.reHeap(); // reorganise with heapsort
            minHeap.storeOutput(minHeap.peek()); // stores the first item

            while(runs){
                flagStart = true;
                if(minHeap.isEmpty()){
                    System.out.println((char)4); // "EOR" char
                    if(flagEnd){
                        minHeap.count = finalCap;
                        break;
                    }
                    else
                        return; // break out of loop
                }
                if(minHeap.peek().compareTo(minHeap.retrieveStore()) >= 0){
                    System.out.println(minHeap.peek()); // output first item
                    str = br.readLine();
                    
                    if(str != null)
                        minHeap.storeOutput(minHeap.replace(str)); // replace the item
                    else{
                        minHeap.storeOutput(minHeap.remove()); // remove the item
                        flagEnd = true; // start setting up flags
                        finalCap++; // start counting for final run
                    }
                }
                else{
                    minHeap.swap(1, minHeap.count); // makes the first item redundant
                    minHeap.count --; // temporarily shortens the heap
                    minHeap.reHeap();

                    if(minHeap.isEmpty()){
                        System.out.println((char)4); // "EOR" char
                        minHeap.count = minHeapCapacity; // restore heap length
                        minHeap.reHeap();
                        minHeap.storeOutput(""); // reset the [0]th array item
                    }
                }
            }
            br.close(); //close the buffered reader
            
            Boolean toShuffle = false; // check if there are items left in the heap that need shuffling along
            if (flagStart && minHeap.count == minHeapCapacity && !flagEnd)
                toShuffle = false;
            else if (flagStart)
                toShuffle = true;

            if (toShuffle) { // when the file is empty and some runs already done
                int j = 1; // count
                for (int i = minHeap.count + 1; i <= minHeapCapacity; i++, j++) { // move everything from minHeapCapacity to size to
                    minHeap.heapArray[j] = minHeap.heapArray[i]; // start at [1]
                }
                minHeap.count = j - 1; // fix capacity value
            }

            if (minHeap.peek() != null) { // for the last run
                minHeap.reHeap(); // reheap final values

                for (int i = minHeap.count + 1; i <= minHeapCapacity; i++) // empty remainder of heap
                    minHeap.heapArray[i] = null;

                while(minHeap.count != 0){ // print out the rest of the items (should already be in order)
                    System.out.println(minHeap.remove());
                }
                System.out.print((char)4); // end of final run
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}