package com.tests.basket.bonus;

import java.util.ArrayList;

public class BonusStore {
    private static ArrayList<Bonus> store = new ArrayList<>();

    public static void addBonusToStore(Bonus bonus){
        store.add(bonus.getId(),bonus);
    }

    public static Bonus getBonus(int idBonus){
        return store.get(idBonus);
    }
}
