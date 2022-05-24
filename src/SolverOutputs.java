import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SolverOutputs {

    /* Class constructor */

    private String outFile;
    private double elapsed;
    BranchAndBound bestSol;
    Item[] items;

    SolverOutputs(String outputFileName, Item[] items, BranchAndBound bbsolution, double elapsedTime)
    {
        outFile = outputFileName;
        bestSol = bbsolution;
        elapsed = elapsedTime;
        items = items;
    }

    /* Auxiliary methods */

    public void printOutputFile() {
        try {
            // PrintWriter out = new PrintWriter(System.currentTimeMillis() + outFile);
            PrintWriter out = new PrintWriter(outFile);
            out.println("***************************************************");
            out.println("*                                                 *");
            out.println("*          RESULTS FROM KNAPSACK INSTANCES         *");
            out.println("*                                                 *");
            out.println("***************************************************");
            out.printf("\r\nElapsed time: %.5f seconds\r\n\r\n", elapsed);
            out.println("\r\n");
            out.println("***************************************************");
            out.println("*                      OUTPUTS                    *");
            out.println("***************************************************");
            out.println("\r\nBEST SOLUTION Using Branch and Bound:\r\n");
            out.println("--------------------------------------------");
            out.println(bestSol.toString()+ "\r\n");
            out.close();
        } catch (IOException exception) {
            System.out.println("Error processing output file: " + exception);
        }

    }
}
