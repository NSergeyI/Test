package com.tests.basket.bonus;

public class Bonus {
    private static int count = 0;
    private int id;
    private int percent;
    private Type type;


    public Bonus(int percent, Type type) {
        id = count++;
        this.percent = percent;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getPercent() {
        return percent;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bonus bonus = (Bonus) o;

        if (id != bonus.id) return false;
        if (percent != bonus.percent) return false;
        return type == bonus.type;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + percent;
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Bonus{" +
                "id=" + id +
                ", percent=" + percent +
                ", type=" + type +
                '}';
    }
}
