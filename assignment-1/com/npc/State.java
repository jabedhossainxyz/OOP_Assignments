package com.npc;

public interface State {
    void talk(Fairy fairy);
    void flattery(Fairy fairy);
    void curse(Fairy fairy);
    void exchange(Fairy fairy);
    void giveGift(Fairy fairy);
}
