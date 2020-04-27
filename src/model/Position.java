package model;

public class Position<E> {
    private E x;
    private E y;

    Position(E x, E y) {
        this.x = x;
        this.y = y;
    }

    public E getX() {
        return x;
    }

    public E getY() {
        return y;
    }
}
