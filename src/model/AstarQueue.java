package model;

import java.util.HashMap;
import java.util.Map;

public class AstarQueue {
    private Map<Node, Integer> map;

    public AstarQueue() {
        map = new HashMap<>();
    }

    public void add(Node node, int value) {
        map.put(node, value);
    }

    public Node popBest() {
        int minValue = Integer.MAX_VALUE;
        Node bestNode = null;

        for (var entry : map.entrySet()) {
            if (entry.getValue() < minValue) {
                minValue = entry.getValue();
                bestNode = entry.getKey();
            }
        }

        map.remove(bestNode);
        return bestNode;
    }

    public boolean empty() {
        return map.size() == 0;
    }
}
