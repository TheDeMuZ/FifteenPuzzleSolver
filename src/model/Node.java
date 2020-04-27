package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    private byte[][] state;
    private List<Direction> path;

    private byte[][] deepCopyOfState(byte[][] state) {
        byte[][] newState = new byte[state.length][state[0].length];

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                newState[i][j] = state[i][j];
            }
        }

        return newState;
    }

    private static Position<Integer> findZero(byte[][] state) {
        for (int x = 0; x < state.length; x++) {
            for (int y = 0; y < state[x].length; y++) {
                if (state[x][y] == 0) {
                    return new Position<>(x, y);
                }
            }
        }

        return null;
    }

    private void doMove(Position<Integer> zeroPosition, int offsetX, int offsetY) {
        int x = zeroPosition.getX() + offsetX;
        int y = zeroPosition.getY() + offsetY;

        this.state[zeroPosition.getX()][zeroPosition.getY()] = this.state[x][y];
        this.state[x][y] = 0;
    }

    public boolean move(Direction direction) {
        if (direction == null) {
            return false;
        }

        if (this.path.size() > 0) {
            int pathSize = this.path.size();

            if (Direction.isOpposite(direction, this.path.get(pathSize - 1))) {
                return false;
            }
        }

        Position<Integer> zeroPosition = Node.findZero(this.state);

        switch (direction) {
            case LEFT: {
                if (zeroPosition.getX() <= 0) {
                    return false;
                }

                this.doMove(zeroPosition, -1, 0);
                break;
            }

            case RIGHT: {
                if (zeroPosition.getX() >= this.state.length - 1) {
                    return false;
                }

                this.doMove(zeroPosition, 1, 0);
                break;
            }

            case UP: {
                if (zeroPosition.getY() <= 0) {
                    return false;
                }

                this.doMove(zeroPosition, 0, -1);
                break;
            }

            case DOWN: {
                if (zeroPosition.getY() >= this.state[0].length - 1) {
                    return false;
                }

                this.doMove(zeroPosition, 0, 1);
                break;
            }
        }

        this.path.add(direction);
        return true;
    }

    private Node(byte[][] state) {
        this.state = state;
        this.path = new ArrayList<Direction>();
    }

    public static Node fromStringList(List<String> lines) /* TODO: walidacja */ {
        String[] line1 = lines.get(0).split(" ");
        int width = Integer.parseInt(line1[0]);
        int height = Integer.parseInt(line1[1]);

        byte[][] state = new byte[width][height];

        for (int y = 0; y < height; y++) {
            String[] values = lines.get(y + 1).split(" ");

            for (int x = 0; x < width; x++) {
                state[x][y] = Byte.parseByte(values[x]);
            }
        }

        return new Node(state);
    }

    public Node(Node node) {
        this.state = this.deepCopyOfState(node.getState());
        this.path = new ArrayList<>(node.getPath());
    }

    public byte[][] getState() {
        return state;
    }

    public List<Direction> getPath() {
        return path;
    }
}