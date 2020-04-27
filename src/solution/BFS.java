package solution;

import model.Direction;
import model.Node;

import java.util.LinkedList;
import java.util.List;

public class BFS extends Solution {
    Node solve(Node node, List<Direction> order) {
        this.start(node);

        LinkedList<Node> queue = new LinkedList<>();
        queue.add(node);

        while (queue.size() > 0) {
            node = queue.poll();
            this.addVisited(node.getState());
            this.setReachedDepth(node.getPath().size());

            if (this.isCorrect(node)) {
                return node;
            }

            if (!this.checkTime()) {
                return null;
            }

            if (node.getPath().size() < Solution.MAX_DEPTH) {
                for (var direction : order) {
                    var child = new Node(node);

                    if (child.move(direction) && !this.wasVisited(child.getState())) {
                        queue.add(child);
                    }
                }
            }
        }

        return null;
    }

    @Override
    public Node solve(Node node, Object arg) {
        return this.solve(node, (List<Direction>)arg);
    }
}