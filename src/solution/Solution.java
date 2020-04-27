package solution;

import model.Direction;
import model.Node;

import java.io.IOException;
import java.util.*;

public abstract class Solution {
    final static int MAX_DEPTH = 20;
    private final static long MAX_TIME = 1000000000L;

    private long beginTime;
    private long endTime;

    private byte[][] correctState = null;
    private int reachedDepth;

    private List<byte[][]> visited;
    private List<byte[][]> processed;

    public long getBeginTime() {
        return beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    byte[][] getCorrectState() {
        return correctState;
    }

    public int getVisitedNodesCount() {
        return visited.size();
    }

    public int getProcessedNodesCount() {
        return processed.size();
    }

    public int getReachedDepth() {
        return reachedDepth;
    }

    void setReachedDepth(int depth) {
        if (this.reachedDepth < depth) {
            this.reachedDepth = depth;
        }
    }

    Solution() {
        this.visited = new ArrayList<>();
        this.processed = new ArrayList<>();
        this.reachedDepth = 0;
    }

    boolean checkTime() {
        this.endTime = System.nanoTime();
        return (this.endTime - this.beginTime < Solution.MAX_TIME);
    }

    boolean isCorrect(Node node) {
        return Arrays.deepEquals(node.getState(), this.correctState);
    }

    private boolean wasProcessed(byte[][] state) {
        for (byte[][] v : this.processed) {
            if (Arrays.deepEquals(v, state)) {
                return true;
            }
        }

        return false;
    }

    void addProcessed(byte[][] state) {
        if (!this.wasProcessed(state)) {
            this.processed.add(state);
        }
    }

    boolean wasVisited(byte[][] state) {
        for (byte[][] v : this.visited) {
            if (Arrays.deepEquals(v, state)) {
                return true;
            }
        }

        return false;
    }

    void addVisited(byte[][] state) {
        if (!this.wasVisited(state)) {
            this.visited.add(state);
        }
    }

    private void createCorrectState(byte[][] state) {
        this.correctState = new byte[state.length][state[0].length];

        for (int y = 0; y < state[0].length; y++) {
            for (int x = 0; x < state.length; x++) {
                this.correctState[x][y] = (byte)(x + y * state.length + 1);
            }
        }

        this.correctState[state.length - 1][state[0].length - 1] = 0;
    }

    void start(Node node) {
        this.beginTime = System.nanoTime();

        if (this.correctState == null) {
            createCorrectState(node.getState());
        }
    }

    public abstract Node solve(Node node, Object arg);
}