import sun.awt.image.ImageWatched;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class Solver {

    /**
     * The main class
     */
    public static void main(String[] args) {
        try {
            solve(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the instance, solve it, and print the solution in the standard output
     */
    public static void solve(String[] args) throws IOException {

        /* DECLARE ALL INSTANCE FIELDS */
        String outputFileName; // output file
        long startTime; // start time for computations
        long endTime; // end time for computations
        double elapsed; // computation time
        SolverOutputs outputs;
        String fileName = null;

        File dir = new File("src/knapsack-data");
        File[] files = dir.listFiles();

        for (File file : files) {
            fileName = file.getPath();
            System.out.println("fileName: " + fileName);
            // read the lines out of the file
            List<String> lines = new ArrayList<String>();

            BufferedReader input = new BufferedReader(new FileReader(fileName));
            try {
                String line = null;
                while ((line = input.readLine()) != null) {
                    lines.add(line);
                }
            } finally {
                input.close();
            }


            // parse the data in the file
            String[] firstLine = lines.get(0).split("\\s+");
            int items = Integer.parseInt(firstLine[0]);
            int capacity = Integer.parseInt(firstLine[1]);

            int[] values = new int[items];
            int[] weights = new int[items];

            for (int i = 1; i < items + 1; i++) {
                String line = lines.get(i);
                String[] parts = line.split("\\s+");

                values[i - 1] = Integer.parseInt(parts[0]);
                weights[i - 1] = Integer.parseInt(parts[1]);
            }

            Item[] objects = new Item[items];
            for (int i = 0; i < items; i++) {
                objects[i] = new Item(values[i], weights[i], i);
            }

            BranchAndBound bb = new BranchAndBound();
            bb.setCapacity(capacity);
            bb.setSize(items);
            startTime = System.nanoTime();
            bb.solve(objects);
            endTime = System.nanoTime();
            elapsed = (endTime - startTime) / 1.0E+9;


            /* PRINT RESULTS TO THE OUTPUTS FILE */

            outputs = new SolverOutputs("src\\knapsack-data-output\\" + Paths.get(fileName).getFileName(), objects, bb, elapsed);
            outputs.printOutputFile();

            /* END OF PROGRAM */

            System.out.println("End of program. Elapsed time = " + elapsed +
                    " seconds.");
        }
    }
}