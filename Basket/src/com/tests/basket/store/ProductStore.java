package com.tests.basket.store;

import java.util.HashMap;
import java.util.Map;

public class ProductStore {
    private static Map<Product, Integer> store;

    public ProductStore() {
        store = new HashMap<Product, Integer>();
    }

    public static void editProductToStore(Product product, int quantity) {
        if (store.containsKey(product)) {
            changeAndCheckQuantityProductToStore(product, quantity);
        } else {
            addProductToStore(product, quantity);
        }
    }

    private static void changeAndCheckQuantityProductToStore(Product product, int quantity) {
        int newQuantityProductInStorage = store.get(product) + quantity;
        if (newQuantityProductInStorage > 0) {
            store.put(product, newQuantityProductInStorage);
        } else {
            if (newQuantityProductInStorage == 0) {
                store.remove(product);
            } else {
                //вставить исключение на - значения
            }
        }
    }

    private static void addProductToStore(Product product, int add) {
        if (add > 0) {
            store.put(product, add);
        } else {
            //вставить исключение на 0 и - значения
        }

    }

    public static int checkProductToStore(Product product) {
        if (store.containsKey(product)) {
            return store.get(product);
        } else {
            //вставить исключение на отсутствие товара
            return -1;
        }

    }

    public static Product getProduct(int idProduct) {
        for (Map.Entry<Product, Integer> entry : store.entrySet()) {
            if (entry.getKey().getId() == idProduct)
                return entry.getKey();
        }
        return null;
    }

    // колличество товара по id
   /* public static int getQuantity(int idProduct) {
        if (store.containsKey(getProduct(idProduct))) {
            return store.get(getProduct(idProduct));
        } else return 0;
    }*/
}
