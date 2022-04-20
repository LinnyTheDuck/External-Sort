# External-Sort

A small program designed to take a file (ideally a text file) and sort each line into alphabetical order using external mergesort

### Usage

> javac \*.java <br>
> cat input | java CreateRuns 25 | java MergeRuns 7 > input.sorted

The argument for the CreateRuns is the number of lines placed into memory at once <br>
The argument for the Mergeruns is for an X way merge

The software won't work with ASCII control characters

Written in 2021, 15/15
