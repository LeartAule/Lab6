package Dragon;

import java.io.IOException;
import java.io.Serializable;

/**
 * Класс координаты
 *
 *
 */


public class Coordinates implements Comparable<Coordinates>, Serializable {
    private Integer x;//Поле не может быть null
    private Integer y;

    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
        this.x = 0;
        this.y = 0;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "{x = " + x + "; y = " + y + "}";
    }

    @Override
    public int compareTo(Coordinates o) {
        int result =this.x.compareTo(o.x);
        if (result==0){
            result = this.y.compareTo(o.y);
        }
        return result;
    }
}
