package com.tests.basket.journal;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Journal {
    private ArrayList<Event> log;
    private int marker;

    public int getMarker() {
        return marker;
    }

    public Journal() {
        log = new ArrayList<>();
        marker = 0;
    }

    // добавляем событие, если маркер не в конце очищаем лог от marker и до конца
    public void addEventToLog(Event event){
        while(log.size() > marker){
            log.remove(marker);
        }
        log.add(event);
        marker++;
    }

    public Event getEvent(int key){
        return log.size()>key&&key>-1? log.get(key):null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Event event:log){
            stringBuilder.append(event.toString()).append("\n");
        }
        stringBuilder.append(marker);
        return stringBuilder.toString();
    }

    public Event udo(){
//        System.out.println("u:"+ (marker-1));
        return marker>-1? log.get(--marker):null;
    }

    public Event redo(){
//        System.out.println("r:"+ marker);
        return marker<log.size()?log.get(marker++):null;
    }
}
