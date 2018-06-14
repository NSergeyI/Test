package com.tests.basket;

import com.tests.basket.bonus.*;
import com.tests.basket.journal.*;
import com.tests.basket.store.*;

import java.util.HashMap;
import java.util.Map;

public class Basket {
    private static int count = 0;
    private int id;
    private final int REMOVE = -1;               //для журнала маркер: удалить

    private Journal journal = new Journal();
    private Map<Product, Integer> productMap = new HashMap<>();
    private Map<Bonus, Integer> bonusMap = new HashMap<>();
    private Bonus bonus;

    public Basket() {
        id = count++;
    }


    //    добавление товара
    public void addProductToBasket(int idProduct, int value) {
        if (value <= ProductStore.getQuantity(idProduct)) {  //наличие товара на складе
            int oldValue = productMap.containsKey(idProduct) ? productMap.get(idProduct) : REMOVE;
            productMap.put(idProduct, value);
            journal.addEventToLog(new Event(State.EDIT_PRODUCT_TO_BASKET, idProduct, oldValue, value));
        } else {
            System.out.println("нет столько товара");
        }
    }

    //    удаление товара
    public void removeProductToBasket(int idProduct) {
        if (productMap.containsKey(idProduct)) {
            checkAndRemoveBonusToProduct(idProduct); //удаляем бонусы для этого товара
            int oldValue = productMap.get(idProduct);
            productMap.remove(idProduct);
            journal.addEventToLog(new Event(State.EDIT_PRODUCT_TO_BASKET, idProduct, oldValue, REMOVE));
        }
    }


    /* изменение товара в корзине
       quality - изменение колличества (+|-)товара,
       Если колличество товара уменьшается до 0
       то удаляется запись о Product
       */
    public void editProductToBasket(int idProduct, int quality) {
        if (productMap.containsKey(idProduct)) {                        //наличие товара в карзине
            int value = productMap.get(idProduct) + quality;
            if (value > 0) {                                            //проверка, в корзине должно что то остатья
                addProductToBasket(idProduct, value);
            } else {
                removeProductToBasket(idProduct);
            }
        } else {
            if (quality > 0) {                                            //что бы не было минуса в корзине
                addProductToBasket(idProduct, quality);
            }
        }
    }

    // добавление бонуса продукту
    public void addBonusToProduct(int idBonus, int idProduct) {
        if (BonusStore.getBonus(idBonus).getType() == Type.PRODUCT) {
            int oldIdProduct = bonusMap.containsKey(idBonus) ? bonusMap.get(idBonus) : 0;
            checkAndRemoveBonusToProduct(idProduct); //удаляем другие бонусы для этого продукта
            bonusMap.put(idBonus, idProduct);
            journal.addEventToLog(new Event(State.EDIT_BONUS_TO_PRODUCT, idBonus, oldIdProduct, idProduct));
        } else {
            System.out.println("этот бонус для корзины");
        }
    }

    //Поиск и удаление записей в bonusMap по idProduct
    public void checkAndRemoveBonusToProduct(int idProduct) {
        if (bonusMap.containsValue(idProduct)) {
            for (Map.Entry<Integer, Integer> entery : bonusMap.entrySet()) {
                if (entery.getValue() == idProduct) {
                    removeBonusToProduct(entery.getKey());
                    break; //больше одной записи не должно быть
                }
            }
        }
    }

    public void removeBonusToProduct(int idBonus) {
        if (bonusMap.containsKey(idBonus)) {
            int oldIdProduct = bonusMap.get(idBonus);
            bonusMap.remove(idBonus);
            journal.addEventToLog(new Event(State.EDIT_BONUS_TO_PRODUCT, idBonus, oldIdProduct, REMOVE));
        }
    }

    // добавление бонуса корзине
    public void addBonusToBasket(int idBonus) {
        if (BonusStore.getBonus(idBonus).getType() == Type.BASKET) {
            int oldBonusId = bonusId;
            bonusId = idBonus;
            journal.addEventToLog(new Event(State.EDIT_BONUS_TO_BASKET, oldBonusId, idBonus));
        }
    }

    //удаление бонуса из корзины
    public void removeBonusToBasket() {
        journal.addEventToLog(new Event(State.EDIT_BONUS_TO_BASKET, bonusId, REMOVE));
        bonusId = REMOVE;
    }

    public void undo() {
        Event curentEvent = journal.undo();
        if (curentEvent != null) {
            switch (curentEvent.getState()) { // операции с товаром здесь чтобы записи о операциях не попали в лог
                case EDIT_PRODUCT_TO_BASKET: {
                    productMap.put(curentEvent.getKey(), curentEvent.getValueOld());
                    break;
                }
                case EDIT_BONUS_TO_PRODUCT: {
                    bonusMap.put(curentEvent.getKey(), curentEvent.getValueOld());
                    break;
                }
                case EDIT_BONUS_TO_BASKET: {
                    bonusId = curentEvent.getValueOld();
                }
            }
        } else {
            System.out.println("ends Undo");
        }
    }

    public void redo() {
        Event curentEvent = journal.redo();
        if (curentEvent != null) {
            switch (curentEvent.getState()) {
                case EDIT_PRODUCT_TO_BASKET: {
                    productMap.put(curentEvent.getKey(), curentEvent.getValueNew());
                    break;
                }
                case EDIT_BONUS_TO_PRODUCT: {
                    bonusMap.put(curentEvent.getKey(), curentEvent.getValueNew());
                    break;
                }
                case EDIT_BONUS_TO_BASKET: {
                    bonusId = curentEvent.getValueNew();
                }
            }
        } else {
            System.out.println("ends Redo");
        }
    }


    public void printBasket() {
        for (Map.Entry<Integer, Integer> entry : productMap.entrySet()) {
            System.out.println(ProductStore.getProduct(entry.getKey()) + "  :" + entry.getValue());
        }

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");

        for (Map.Entry<Integer, Integer> entry : bonusMap.entrySet()) {
            System.out.println(BonusStore.getBonus(entry.getKey()) + "  :" + ProductStore.getProduct(entry.getValue()));
        }

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");

        System.out.println(BonusStore.getBonus(bonusId));

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");

        System.out.println(journal);
    }

    public double printReceipt() {
        double result = 0;
        System.out.println("Товар    цена    количество  бонус сумма");
        for (Map.Entry<Integer, Integer> entry : productMap.entrySet()) {
            int bonus = 0;
            for (Map.Entry<Integer, Integer> entryBonus : bonusMap.entrySet()) {
                if (entry.getKey().equals(entryBonus.getValue())) bonus = entryBonus.getKey();
            }
            double summ = ProductStore.getProduct(entry.getKey()).getPrice() * entry.getValue() * (100 - BonusStore.getBonus(bonus).getPercent()) / 100;
            result = result + summ;
            System.out.printf(ProductStore.getProduct(entry.getKey()).getName() + "  %4.2f%5d       %5d     %5.2f%n",
                    ProductStore.getProduct(entry.getKey()).getPrice(),
                    entry.getValue(),
                    BonusStore.getBonus(bonus).getPercent(),
                    summ);


        }
        System.out.println("Бонус на корзину :" + (bonusId == -1 ? "0" : BonusStore.getBonus(bonusId).getPercent()) + "%");
        result = result * (100 - (bonusId == -1 ? 0 : BonusStore.getBonus(bonusId).getPercent())) / 100;
        System.out.printf("ИТОГО :%4.2f", result);
        return result;

    }
    public Map<Product, Integer> getProductMap() {
        return productMap;
    }

    public Map<Bonus, Integer> getBonusMap() {
        return bonusMap;
    }

    public Bonus getBonus() {
        return bonus;
    }

}

