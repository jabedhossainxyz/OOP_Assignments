package com.npc;

public class Fairy {
    private State state;

    public Fairy() {
        // Initial state
        this.state = new HappyState();
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void talk() {
        state.talk(this);
    }

    public void flattery() {
        state.flattery(this);
    }

    public void curse() {
        state.curse(this);
    }

    public void exchange() {
        state.exchange(this);
    }

    public void giveGift() {
        state.giveGift(this);
    }
}
