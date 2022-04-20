The software will work on the premise that the person generating the text file 
doesn't intentionally insert ascii control characters into the text, specifically 
the ascii characters 0x4 (end of transmission).

One heap is for strings and the other is for nodes where nodes also keep a 
reference to the original file they came from. The reason that there are two heaps
is due to lack of time to merge the two heaps and thus resulted in the fear of 
screwing up all of the code if we rushed things.

Have a nice day! :)
