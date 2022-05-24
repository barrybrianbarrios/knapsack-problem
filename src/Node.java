class Node {
    // Upper Bound: Best case
    // (Fractional Knapsack)
    float ub;

    // Lower Bound: Worst case
    // (0/1)
    float lb;

    // Level of the node in
    // the decision tree
    int level;

    // Stores if the current
    // item is selected or not
    boolean flag;

    // Total Value: Stores the
    // sum of the values of the
    // items included
    float tv;

    // Total Weight: Stores the sum of
    // the weights of included items
    float tw;
    public Node() {}
    public Node(Node cpy)
    {
        this.tv = cpy.tv;
        this.tw = cpy.tw;
        this.ub = cpy.ub;
        this.lb = cpy.lb;
        this.level = cpy.level;
        this.flag = cpy.flag;
    }
}
 