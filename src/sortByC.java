import java.util.Comparator;
// Comparator to sort based on lower bound
class sortByC implements Comparator<Node> {
    public int compare(Node a, Node b)
    {
        boolean temp = a.lb > b.lb;
        return temp ? 1 : -1;
    }
}