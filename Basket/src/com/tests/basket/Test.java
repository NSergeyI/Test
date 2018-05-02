package com.tests.basket;

import com.tests.basket.bonus.Bonus;
import com.tests.basket.bonus.BonusStore;
import com.tests.basket.bonus.Type;
import com.tests.basket.store.Product;
import com.tests.basket.store.ProductStore;

import java.util.HashMap;

/*
        1.     Реализовать корзину товаров со следующей функциональностью:

                ·       - Добавление товара в корзину

                ·       - Удаление товара из корзины

                ·       - Изменение количества товара в корзине

                ·       - Применение купона к товару

                ·       - Применение купона к корзине

                ·       - Undo (отмена последней операции) неограниченное число раз

                ·       - Redo (применение последней отмененной операции) неограниченное число раз

                ·       - Печать чека

        2.     Купон дает право на скидку, скидка может отличаться в зависимости от купона. Купон может быть применен либо только к товару, либо только к корзине.

        3.     Функциональность желательно покрыть юнит-тестами.
*/



public class Test {
    ProductStore productStore = new ProductStore();
    BonusStore bonusStore = new BonusStore();
    Basket basket = new Basket();

    public static void main(String[] args) {
        Test test = new Test();
        test.napolnenie();
//        test.productToBasket();
//        test.bonusToProduct();
//        test.bonusToBasket();
//        test.redoUndo();
        test.tests();
        test.basket.printReceipt();
//        test.print();

    }

    public void napolnenie() {
        for (int i = 0; i < 20; i++) {
            ProductStore.editProductToStore(new Product("Product" + i), i * 10);
        }

        for (int i = 0; i < 20; i++) {
            BonusStore.addBonusToStore(new Bonus(i, i % 2 == 0 ? Type.BASKET : Type.PRODUCT));
        }
    }

    public void productToBasket() {
        basket.addProductToBasket(2, 10);
        basket.addProductToBasket(3, 150);
        basket.addProductToBasket(1, 10);
        basket.addProductToBasket(5, 10);
        basket.addProductToBasket(2, 30);
        basket.addProductToBasket(6, 10);
        basket.editProductToBasket(1, 10);
        basket.editProductToBasket(2, 5);
        basket.editProductToBasket(5, 30);
        basket.editProductToBasket(6, -5);
        basket.editProductToBasket(6, 10);
        basket.editProductToBasket(7, -5);
        basket.editProductToBasket(3, 5);
        basket.removeProductToBasket(2);
    }

    public void bonusToProduct() {
        basket.addBonusToProduct(1, 1);
        basket.addBonusToProduct(3, 2);
        basket.addBonusToProduct(2, 3);
        basket.addBonusToProduct(5, 5);
        basket.addBonusToProduct(7, 1);
        basket.addBonusToProduct(7, 3);
        basket.removeBonusToProduct(3);
    }

    public void bonusToBasket() {
        basket.addBonusToBasket(2);
        basket.addBonusToBasket(3);
        basket.removeBonusToBasket();
        basket.addBonusToBasket(6);
    }

    public void redoUndo() {
        basket.undo();
        basket.undo();
        basket.undo();
        basket.undo();
        basket.undo();
        basket.redo();
        basket.redo();
        basket.redo();
    }

    public boolean tests() {
        boolean result;

        basket.addProductToBasket(2, 10);
        basket.addProductToBasket(3, 150);
        basket.addProductToBasket(1, 10);
        basket.addProductToBasket(5, 10);
        basket.addProductToBasket(2, 30);
        basket.addProductToBasket(6, 10);
        basket.editProductToBasket(1, 10);
        basket.editProductToBasket(2, 5);
        basket.editProductToBasket(5, 30);
        basket.editProductToBasket(6, -5);
        basket.editProductToBasket(6, 10);
        basket.editProductToBasket(7, -5);
        basket.editProductToBasket(3, 5);
        basket.removeProductToBasket(2);

        result = check("products in basket",new HashMap<Integer, Integer>() {{
            put(1, 10);
            put(3, 5);
            put(5, 40);
            put(6, 15);
        }}, new HashMap<Integer, Integer>(), -1);


        basket.addBonusToProduct(1, 1);
        basket.addBonusToProduct(3, 2);
        basket.addBonusToProduct(2, 3);
        basket.addBonusToProduct(5, 5);
        basket.addBonusToProduct(7, 1);
        basket.addBonusToProduct(7, 3);

        result = result && check("product bonus",new HashMap<Integer, Integer>() {{
            put(1, 10);
            put(3, 5);
            put(5, 40);
            put(6, 15);
        }}, new HashMap<Integer, Integer>() {{
            put(3, 2);
            put(5, 5);
            put(7, 3);
        }}, -1);

        basket.removeBonusToProduct(3);
        basket.addBonusToBasket(2);
        basket.addBonusToBasket(3);
        basket.removeBonusToBasket();
        basket.addBonusToBasket(6);

        result = result && check("basket bonus",new HashMap<Integer, Integer>() {{
            put(1, 10);
            put(3, 5);
            put(5, 40);
            put(6, 15);
        }}, new HashMap<Integer, Integer>() {{
            put(5, 5);
            put(7, 3);
        }}, 6);

        basket.undo();
        basket.undo();
        basket.undo();
        basket.undo();
        basket.undo();
        basket.redo();
        basket.redo();
        basket.redo();


        basket.addBonusToProduct(1, 1);
        basket.addBonusToProduct(3, 2);

        result = result && check("undo/redo",new HashMap<Integer, Integer>() {{
            put(1, 10);
            put(3, 5);
            put(5, 40);
            put(6, 15);
        }}, new HashMap<Integer, Integer>() {{
            put(1, 1);
            put(3, 2);
            put(5, 5);
            put(7, 3);
        }}, 2);

        return result;
    }

    public boolean check(String name, HashMap<Integer, Integer> products, HashMap<Integer, Integer> bonus, int bonusBasket) {
        boolean result = products.toString().equals(basket.getProductMap().toString()) && bonus.toString().equals(basket.getBonusMap().toString()) && bonusBasket == basket.getBonusId();
        System.out.print(name);
        System.out.println(result?": OK":": NOT OK");
        return result;
    }


    public void print() {
        basket.printBasket();
    }


}