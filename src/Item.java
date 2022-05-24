import java.util.*;
class Item {

    // Stores the weight
    // of items
    float weight;

    // Stores the values
    // of items
    int value;

    // Stores the index
    // of items
    int idx;
    public Item() {}
    public Item(int value, float weight,
                int idx)
    {
        this.value = value;
        this.weight = weight;
        this.idx = idx;
    }
}