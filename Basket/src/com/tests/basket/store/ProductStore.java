package com.tests.basket.store;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.HashMap;
import java.util.Map;

public class ProductStore {
    private static Map<Product, Integer> store;

    public ProductStore() {
        store = new HashMap<Product, Integer>();
    }

    public static int editProductToStore(Product product, int quantity) {
        if (store.containsKey(product)) {
            int storage = store.get(product);
            return storage + quantity >= 0 ? store.put(product, storage + quantity) : storage + quantity;
        } else {
            store.put(product, quantity);
            return 0;
        }
    }

    public static int checkProductToStore(int idProduct) {
        Product product = getProduct(idProduct);
        return product == null ? -1 : store.get(product);
    }

    public static Product getProduct(int idProduct) {
        for (Map.Entry<Product, Integer> entry : store.entrySet()) {
            if (entry.getKey().getId() == idProduct) return entry.getKey();
        }
        return null;
    }

    // колличество товара по id
    public static int getQuantity(int idProduct){
        if (store.containsKey(getProduct(idProduct))){
            return store.get(getProduct(idProduct));
        } else return 0;
    }
}
