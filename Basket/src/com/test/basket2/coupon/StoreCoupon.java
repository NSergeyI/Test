package com.test.basket2.coupon;

import java.util.ArrayList;

public class StoreCoupon {
    private static ArrayList<Coupon> store = new ArrayList<>();

    public static void addCouponToStore(Coupon coupon){
        store.add(coupon);
    }

    public static ArrayList<Coupon> getStore() {
        return store;
    }
}


