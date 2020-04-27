package solution;

import model.AstarQueue;
import model.Direction;
import model.Node;


public class Astar extends Solution {
    private int hammingDistance(byte[][] state1, byte[][] state2) {
        int value = 0;

        for (int i = 0; i < state1.length; i++) {
            for (int j = 0; j < state1[0].length; j++) {
                if (state1[i][j] != state2[i][j]) {
                    value++;
                }
            }
        }

        return value;
    }

    private int manhattanDistance(byte[][] state1, byte[][] state2) {
        int value = 0;

        for (int i = 0; i < state1.length; i++) {
            for (int j = 0; j < state1[0].length; j++) {
                value += Math.abs(state1[i][j] - state2[i][j]);
            }
        }

        return  value;
    }

    private int f(Node node, String metrics) {
        int g = node.getPath().size();
        int h = (metrics.equals("hamm") ? hammingDistance(node.getState(), this.getCorrectState()) : manhattanDistance(node.getState(), this.getCorrectState()));
        return g + h;
    }

    public Node solve(Node node, String metrics) {
        this.start(node);

        AstarQueue queue = new AstarQueue();
        queue.add(node, 0);

        while (!queue.empty()) {
            node = queue.popBest();
            this.addVisited(node.getState());
            this.setReachedDepth(node.getPath().size());

            if (this.isCorrect(node)) {
                return node;
            }

            if (!this.checkTime()) {
                return null;
            }

            for (var direction : Direction.all()) {
                Node child = new Node(node);

                if (child.move(direction) && !this.wasVisited(child.getState())) {
                    queue.add(child, this.f(child, metrics));
                    this.addProcessed(child.getState());
                }
            }
        }

        return null;
    }

    @Override
    public Node solve(Node node, Object arg) {
        return solve(node, (String)arg);
    }
}