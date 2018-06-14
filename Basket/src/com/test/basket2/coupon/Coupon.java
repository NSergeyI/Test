package com.test.basket2.coupon;

public class Coupon {
    private static int count = 0;
    private int id;
    private Type type;
    private int percent;
    private String name;

    public Coupon(Type type, int percent, String name) {
        id = ++count;
        this.type = type;
        this.percent = percent;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public int getPercent() {
        return percent;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coupon coupon = (Coupon) o;

        if (id != coupon.id) return false;
        if (percent != coupon.percent) return false;
        return type == coupon.type;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + type.hashCode();
        result = 31 * result + percent;
        return result;
    }
}
