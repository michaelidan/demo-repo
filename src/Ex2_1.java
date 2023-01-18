
public class Ex2_1 {

    public static String[] createTextFiles(int n, int seed, int bound) {
        // This function creates n text files with a random number of lines.
        // The number of lines in each file is determined using a helper function,
        // and the seed and bound parameters.
        // Each line in the text files consists of a random sentence with at least 10 characters.
        // The function returns an array of the file names.
        String[] fileNames = new String[n];
        Random rand = new Random(seed);
        for (int i = 0; i < n; i++) {
            try{
            File fileName = new File("file_" + (i + 1)+".txt");
            fileName.createNewFile();
            fileNames[i] = fileName.getName();
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
                // Calculates the number of lines for the current file.
                int numLines = rand.nextInt(bound);
                for (int j = 0; j < numLines; j++) {
                    // Writes a line to the file.
                    bw.write("World Hello \n");
                }
                bw.close();
            } catch (IOException e) {
                // If an error occurs while writing the file, print the stack trace to the screen.
                e.printStackTrace();
            }
        }
        // Return the array of file names.
        return fileNames;
    }

/*
    /**
     * This function returns the total number of lines in all the files specified in the fileNames array.
     * @param fileNames an array of file names to be processed
     * @return the total number of lines in all the specified files
     */
    public static int getNumOfLines(String[] fileNames) {
        // Initialize a counter for the number of lines
        int numLines = 0;
        // Iterate through all the files
        for (String fileName : fileNames) {
            // Create a BufferedReader to read from the file
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                // Read lines from the file until there are no more lines to read
                while (br.readLine() != null) {
                    // Increment the line counter for each line read
                    numLines++;
                }
            } catch (IOException e) {
                // Print the stack trace of the exception if there is an error reading from the file
                e.printStackTrace();
            }
        }
        // Return the total number of lines
        return numLines;
    }

    public static int getNumOfLinesThreads(String[] fileNames) {
        // This function calculates the total number of lines in all the files specified in the 'fileNames' array.
        // It does this by creating a new 'LineCounterThread' for each file and starting it.
        // The 'LineCounterThread' class extends the Thread class and overrides the run() method to calculate
        // the number of lines in a file.
        // The function then waits for each thread to finish using the 'join()' method and adds the number of lines
        // calculated by each thread to the 'numLines' variable.
        // The total number of lines in all the files is then returned.
        int numLines = 0;
        for (String fileName : fileNames) {
            // Create a new 'LineCounterThread' for the current file.
            LineCounterThread thread = new LineCounterThread(fileName);
            // Start the thread.
            thread.start();
            try {
                // Wait for the thread to finish.
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Add the number of lines calculated by the thread to the total.
            numLines += thread.getNumLines();
        }
        return numLines;
    }



    private static class LineCounterThread extends Thread {
        //This class extends the Thread class and overrides the run() method.
        //It counts the number of lines in a file and stores the result in the "numLines" field.

        //The file to read from
        private String fileName;
        //The number of lines in the file
        private int numLines;

        //Constructor that sets the file name
        public LineCounterThread(String fileName) {
            this.fileName = fileName;
        }

        //Returns the number of lines
        public int getNumLines() {
            return numLines;
        }

        @Override
        public void run() {
            //The run() method is called when the thread is started
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                //Wrapping the FileReader in a BufferedReader allows for efficient reading of the file
                //We loop through each line of the file and increment the numLines variable
                while (br.readLine() != null) {
                    numLines++;
                }
            } catch (IOException e) {
                //If an IOException occurs (e.g. the file is not found), print the stack trace
                e.printStackTrace();
            }
        }
    }



    public static void createTextFilesTimed(int n, int seed, int bound) {
        // This function measures the time it takes to run the createTextFiles() function.
        // The start time is recorded before createTextFiles() is called, and the end time is recorded
        // after it returns. The difference between the end time and start time is the execution time
        // of createTextFiles() and is printed to the console.
        long startTime = System.currentTimeMillis();
        createTextFiles(n, seed, bound);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken to create files: " + (endTime - startTime) + " milliseconds");
    }

    public static void getNumOfLinesTimed(String[] fileNames) {
        // This function measures the time it takes to run the getNumOfLines() function.
        // The start time is recorded before getNumOfLines() is called, and the end time is recorded
        // after it returns. The difference between the end time and start time is the execution time
        // of getNumOfLines() and is printed to the console.
        long startTime = System.currentTimeMillis();
        getNumOfLines(fileNames);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Execution time for getNumOfLinesTimed: " + totalTime + " milliseconds");
    }

}
