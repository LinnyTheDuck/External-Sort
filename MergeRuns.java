/// Stefenie Pickston 1506427
/// Holly Smallwood 1505405

import java.io.*;

public class MergeRuns {

    public static File[] fileArray; // array of files
    public static PrintWriter pw; // printwriter
    public static BufferedReader br; // buff readers

    public static void main(String[] args) {
        try {
            int k; // gets k runs
            if (args.length != 1)
                k = 2; // default value is 2
            else if (Integer.parseInt(args[0]) < 1)
                k = 2;
            else
                k = Integer.parseInt(args[0]);

            DistributeRuns dr = new DistributeRuns(k); // go to distribute runs
            fileArray = dr.returnFileArry(); // return the file array

            reformat(merge()); // merge and reformat
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // uses replacement selection to merge all files into one file
    public static File merge(){
        try {
            checkIfAllFiles(); // get rid of any extra empty files for conveniences sake

            File[] outFileArray = new File[fileArray.length]; // output run files
            for(int i = 0; i < fileArray.length; i++){ // initialises the output files
                File temp = File.createTempFile("tempFile" + (i + 1), ".txt");
                outFileArray[i] = temp; // adds to array
            }

            BufferedReader[] brArray = new BufferedReader[fileArray.length]; // buffered reader array
            for(int i = 0; i < fileArray.length; i++){ // initialises the br array
                BufferedReader br = new BufferedReader(new FileReader(fileArray[i]));
                brArray[i] = br; // adds to array
            }

            // node array and heap
            Node[] nodeArray = new Node[fileArray.length];
            NodeHeap nodeHeap = new NodeHeap(fileArray.length);

            // a file/run bool is marked true when the file/run has ended
            Boolean[] flagFileArray = new Boolean[fileArray.length]; // bools to check if each file has reached the end
            Boolean[] flagRunArray = new Boolean[fileArray.length]; // bools to check if a run has finished
            for(int i = 0; i < fileArray.length; i++){ // initialises the bool array
                flagFileArray[i] = false;
                flagRunArray[i] = false;
            }

            Boolean files =  true;
            int fileNum = 0;

            while(files){
                if(onlyFirst()) // if only one file in fileArray
                    break;

                for(int i = 0; i < fileArray.length; i++) // resets the file bool array
                    flagFileArray[i] = false;

                while(files){ // while all the files in fileArray haven't ended
                    if(allTrue(flagFileArray)) // break if all files have ended
                        break;
                    pw = new PrintWriter(new BufferedWriter(new FileWriter(outFileArray[fileNum],true)));
                    
                    //REPLACEMENT SELECTION
                    //take first line from each file and load into heap and reheap
                    for(int i = 0; i < fileArray.length; i++){
                        String str = brArray[i].readLine(); // read the line
                        nodeArray[i] = new Node(str, i); // place in node array
                        if(nodeArray[i].value == null){
                            flagFileArray[i] = true;
                            flagRunArray[i] = true;
                        }
                    }

                    nodeHeap.load(nodeArray); // load all the nodes into the heap
                    nodeHeap.reHeap(); // reheap

                    while(files){
                        if(allTrue(flagRunArray) || nodeHeap.peek() == null || nodeHeap.peek().value == null){ // break if all runs have ended
                            break;
                        }

                        pw.println(nodeHeap.peek().value); // output the first item
                        pw.flush();

                        // read the line from the peeks ref file
                        int fileRef = nodeHeap.peek().ref;
                        String str = brArray[fileRef].readLine(); 
                        if(str != null){
                            if(str.equals("")){ // if read (char)4 then mark end of run
                                flagRunArray[fileRef] = true; 
                                nodeHeap.remove(); // remove first item
                            }  
                            else{
                                Node node = new Node(str, fileRef); // new node from next line
                                nodeHeap.replace(node); // replace the item from ref file
                                nodeHeap.reHeap(); // reheap
                            }
                        }
                        else
                            flagFileArray[fileRef] = true;
                    }

                    if(!allTrue(flagFileArray) && allTrue(flagRunArray)){
                        pw.println(String.valueOf((char)4)); // reached the end of the merged run
                        pw.flush();

                        for (Node n : nodeArray) // reset the node array
                            n = null;

                        if(fileNum == fileArray.length - 1) // if the last file has been reached
                            fileNum = 0;
                        else // else go to next file
                            fileNum++;
                    }

                    for(int i = 0; i < fileArray.length; i++) // initialises the bool array
                        flagRunArray[i] = false;
                }

                fileNum = 0; // reset the file number

                for(int i = 0; i < fileArray.length; i++) // close all the brs
                    brArray[i].close();

                for(int i = 0; i < fileArray.length; i++){ // empty the files in file array
                    PrintWriter delete = new PrintWriter(fileArray[i]);
                    delete.close();
                }

                File[] temp = new File[fileArray.length]; // swap the file arrays over
                for (int i = 0; i < fileArray.length; i++) 
                    temp[i] = fileArray[i];

                for (int i = 0; i < fileArray.length; i++) 
                    fileArray[i] = outFileArray[i];

                for (int i = 0; i < fileArray.length; i++) 
                    outFileArray[i] = temp[i];

                for(int i = 0; i < fileArray.length; i++){ // reopen the br array
                    BufferedReader br = new BufferedReader(new FileReader(fileArray[i]));
                    brArray[i] = br; // adds to array
                }
            }

            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileArray[0]; // return the first file
    }

    // checks if all the values in a bool array are true
    private static Boolean allTrue(Boolean[] values) {
        for (Boolean value : values) {
            if (!value)
                return false;
        }
        return true;
    }

    // checks if the first value is true and the rest are false
    private static Boolean onlyFirst(){
        try{
        BufferedReader br; // buffered reader
        int count = 0;
        Boolean bool = false; // switches to true if everything from index 2 onwards is an empty file
        
        for(int i = 1; i < fileArray.length; i++){
            br = new BufferedReader(new FileReader(fileArray[i]));
            String s = br.readLine();
            if(s != null) // if the file is not empty
                count++; // count it
        }

        if(count == 0) // are any of the files from index 2 empty?
            return true;
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    // shortens fileArray if needed
    private static void checkIfAllFiles(){
        try{
        BufferedReader br; // buffered reader
        int count = 0; // counts how many files are empty

        for(int i = 0; i < fileArray.length; i++){
            br = new BufferedReader(new FileReader(fileArray[i]));
            String s = br.readLine();
            if(s != null) // if the file is not empty
                count++; // count it
        }
            
        File[] temp = new File[count]; // temp file array

        for(int i = 0; i < count; i++) // transfer the not empty files
            temp[i] = fileArray[i];

        fileArray = temp; // overwrite fileArray
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    // prints everything out onto the console which puts it into > file.txt
    private static void reformat(File f) {
        try {
            BufferedReader b = new BufferedReader(new FileReader(f));
            String s; // reads ascii values, cast to char later

            while ((s = b.readLine()) != null) { // check end of transmission
                if(!s.equals(String.valueOf((char)4)))
                    System.out.println(s); // print out the character
            }

            b.close(); // close the reader
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
