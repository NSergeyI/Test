package com.tests.basket.journal;

public class Event {
    private static int count = 0;
    private final int id;
    private final State state;
    private final int key;
    private final int valueOld;
    private final int valueNew;

    public Event(State state, int key, int valueOld, int valueNew) {
        id = count++;
        this.state = state;
        this.key = key;
        this.valueOld = valueOld;
        this.valueNew = valueNew;
    }

    public Event(State state, int valueOld, int valueNew) {
        id = count++;
        key = -1;
        this.state = state;
        this.valueOld = valueOld;
        this.valueNew = valueNew;
    }

    public State getState() {
        return state;
    }

    public int getKey() {
        return key;
    }

    public int getValueOld() {
        return valueOld;
    }

    public int getValueNew() {
        return valueNew;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", state=" + state +
                ", key=" + key +
                ", valueOld=" + valueOld +
                ", valueNew=" + valueNew +
                '}';
    }
}
