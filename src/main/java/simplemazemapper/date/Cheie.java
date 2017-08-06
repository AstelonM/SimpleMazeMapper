package main.java.simplemazemapper.date;

public class Cheie {
    private int x;
    private int y;

    public Cheie(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cheie cheie = (Cheie) o;

        return x == cheie.x && y == cheie.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
