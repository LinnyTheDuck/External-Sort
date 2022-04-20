/// Stefenie Pickston 1506427
/// Holly Smallwood 1505405

import java.util.*;
import java.io.*;

public class DistributeRuns {
    public Scanner sc = new Scanner(System.in); // accept output from createRuns as input using a scanner
    public File[] fileArray;

    public DistributeRuns(int i) {
        if (i <= 1) // default value instead of program crashing
            i = 2;

        fileArray = new File[i]; // array for storing temporary files

        try {
            //File text = new File("MobyDick.txt"); // for debugging
            //sc = new Scanner(text); // also debugging

            for (int j = 0; j < i; j++) { // creating i number of temporary files
                // File temp = new File("tempFile" + (j + 1) + ".txt"); // for debugging
                // temp.createNewFile(); // also debugging

                File temp = File.createTempFile("tempFile" + (j + 1), ".txt");
                fileArray[j] = temp; // adds to array
            }

            int incrementCurr = 0;
            Boolean distributing = true;
            String line, delim = String.valueOf((char)4); // + "";
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileArray[incrementCurr], true)));

            while(distributing){
                if(!sc.hasNextLine()) // check if there is a next line
                    break;
                else // if there is then save it
                    line = sc.nextLine();
                
                pw.println(line); // print it

                if(line.equals(delim)){
                    pw.close(); // close the printwriter
                    incrementCurr++; // increment which file to read
                    if(incrementCurr > i - 1) // check if we need to go back to the first file
                        incrementCurr = 0;
                    pw = new PrintWriter(new BufferedWriter(new FileWriter(fileArray[incrementCurr], true)));
                }

            }
            sc.close(); // close the scanner
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public File[] returnFileArry() { 
        return fileArray; // returns the file array
    }
}
