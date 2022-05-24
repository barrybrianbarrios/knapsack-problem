import java.util.Arrays;
import java.util.PriorityQueue;

class BranchAndBound {

    private static int size;
    private static float capacity;
    // final_path -> Boolean array to store
    // the result of selection array when
    // it reached the last level
    boolean finalPath[];
    // final_lb -> Minimum lower bound
    // of all the paths that reached
    // the final level
    private  float minLB = 0, finalLB
            = Integer.MAX_VALUE;


    public static void setCapacity(float capacity) {
        BranchAndBound.capacity = capacity;
    }

    public static void setSize(int size) {
        BranchAndBound.size = size;
    }

    // Function to calculate upper bound
    // (includes fractional part of the items)
    static float upperBound(float tv, float tw,
                            int idx, Item arr[]) {
        float value = tv;
        float weight = tw;
        for (int i = idx; i < size; i++) {
            if (weight + arr[i].weight
                    <= capacity) {
                weight += arr[i].weight;
                value -= arr[i].value;
            } else {
                value -= (float) (capacity
                        - weight)
                        / arr[i].weight
                        * arr[i].value;
                break;
            }
        }
        return value;
    }

    // Calculate lower bound (doesn't
    // include fractional part of items)
    static float lowerBound(float tv, float tw,
                            int idx, Item arr[]) {
        float value = tv;
        float weight = tw;
        for (int i = idx; i < size; i++) {
            if (weight + arr[i].weight
                    <= capacity) {
                weight += arr[i].weight;
                value -= arr[i].value;
            } else {
                break;
            }
        }
        return value;
    }

    static void assign(Node a, float ub, float lb,
                       int level, boolean flag,
                       float tv, float tw) {
        a.ub = ub;
        a.lb = lb;
        a.level = level;
        a.flag = flag;
        a.tv = tv;
        a.tw = tw;
    }

    public void solve(Item arr[]) {
        // Sort the items based on the
        // profit/weight ratio
        Arrays.sort(arr, new sortByRatio());

        Node current, left, right;
        current = new Node();
        left = new Node();
        right = new Node();

        // min_lb -> Minimum lower bound
        // of all the nodes explored


        current.tv = current.tw = current.ub
                = current.lb = 0;
        current.level = 0;
        current.flag = false;

        // Priority queue to store elements
        // based on lower bounds
        PriorityQueue<Node> pq
                = new PriorityQueue<Node>(
                new sortByC());

        // Insert a dummy node
        pq.add(current);

        // curr_path -> Boolean array to store
        // at every index if the element is
        // included or not
        boolean currPath[] = new boolean[size];
        finalPath = new boolean[size];
        while (!pq.isEmpty()) {
            current = pq.poll();
            if (current.ub > minLB
                    || current.ub >= finalLB) {
                // if the current node's best case
                // value is not optimal than minLB,
               // Don't explore that node ...
                continue;
            }

            if (current.level != 0)
                currPath[current.level - 1]
                        = current.flag;

            if (current.level == size) {
                if (current.lb < finalLB) {
                    // Reached last level
                    for (int i = 0; i < size; i++)
                        finalPath[arr[i].idx]
                                = currPath[i];
                    finalLB = current.lb;
                }
                continue;
            }

            int level = current.level;

            // right node -> Excludes current item
            // Hence, cp, cw will obtain the value
            // of that of parent
            assign(right, upperBound(current.tv,
                    current.tw,
                    level + 1, arr),
                    lowerBound(current.tv, current.tw,
                            level + 1, arr),
                    level + 1, false,
                    current.tv, current.tw);

            if (current.tw + arr[current.level].weight
                    <= capacity) {

                // left node -> includes current item
                // c and lb should be calculated
                // including the current item.
                left.ub = upperBound(
                        current.tv
                                - arr[level].value,
                        current.tw
                                + arr[level].weight,
                        level + 1, arr);
                left.lb = lowerBound(
                        current.tv
                                - arr[level].value,
                        current.tw
                                + arr[level].weight,
                        level + 1,
                        arr);
                assign(left, left.ub, left.lb,
                        level + 1, true,
                        current.tv - arr[level].value,
                        current.tw
                                + arr[level].weight);
            }

            // If the left node cannot
            // be inserted
            else {

                // Stop the left node from
                // getting added to the
                // priority queue
                left.ub = left.lb = 1;
            }

            // Update minLB
            minLB = Math.min(minLB, left.lb);
            minLB = Math.min(minLB, right.lb);

            if (minLB >= left.ub)
                pq.add(new Node(left));
            if (minLB >= right.ub)
                pq.add(new Node(right));
        }
        System.out.println("Items taken "
                + "into the knapsack are");
        for (int i = 0; i < size; i++) {
            if (finalPath[i])
                System.out.print("1 ");
            else
                System.out.print("0 ");
        }
        System.out.println("\nMaximum profit"
                + " is " + (-finalLB));
    }


    /* toString method */
    public String toString()
    {
        String s = "";
        s = s.concat("\r\n");
        s = s.concat("Items taken "
                + "into the knapsack are ");
        for (int i = 0; i < size; i++) {
            if (finalPath[i])
                s = s.concat("1 ");
            else
                s = s.concat("0 ");
        }
        s = s.concat("\nMaximum profit"
                + " is " + (-finalLB));
        s = s.concat("\r\n\r\n");
        return s;
    }

}