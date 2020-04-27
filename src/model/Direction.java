package model;

import exceptions.DirectionException;

import java.util.ArrayList;
import java.util.List;

public enum Direction {
    LEFT('L'),
    RIGHT('R'),
    UP('U'),
    DOWN('D');

    private char value;

    Direction(char value) {
        this.value = value;
    }

    public static boolean isOpposite(Direction d1, Direction d2) {
        return (d1 == Direction.LEFT && d2 == Direction.RIGHT) ||
                (d1 == Direction.RIGHT && d2 == Direction.LEFT) ||
                (d1 == Direction.UP && d2 == Direction.DOWN) ||
                (d1 == Direction.DOWN && d2 == Direction.UP);
    }

    public static List<Direction> all() {
        return List.of(Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN);
    }

    public static List<Direction> listFromString(String directions) throws DirectionException {
        List<Direction> directionList = new ArrayList<>();

        for (int i = 0; i < directions.length(); i++) {
            switch (directions.charAt(i)) {
                case 'L': {
                    directionList.add(Direction.LEFT);
                    break;
                }

                case 'R': {
                    directionList.add(Direction.RIGHT);
                    break;
                }

                case 'U': {
                    directionList.add(Direction.UP);
                    break;
                }

                case 'D': {
                    directionList.add(Direction.DOWN);
                    break;
                }

                default: {
                    throw new DirectionException(directions.charAt(i));
                }
            }
        }

        return directionList;
    }

    public static String listToString(List <Direction> directions) {
        StringBuilder builder = new StringBuilder();

        for (Direction direction : directions) {
            builder.append(direction);
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        return Character.toString(value);
    }
}