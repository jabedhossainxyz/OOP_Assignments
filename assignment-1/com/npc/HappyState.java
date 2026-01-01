package com.npc;

public class HappyState implements State {
    @Override
    public void talk(Fairy fairy) {
        System.out.println("The fairy smiles and chats happily with you.");
    }

    @Override
    public void flattery(Fairy fairy) {
        System.out.println("The fairy giggles. She loves the compliment!");
    }

    @Override
    public void curse(Fairy fairy) {
        System.out.println("The fairy is shocked by your rudeness! She becomes angry.");
        fairy.setState(new AngryState());
    }

    @Override
    public void exchange(Fairy fairy) {
        System.out.println("The fairy happily exchanges equipment with you.");
    }

    @Override
    public void giveGift(Fairy fairy) {
        System.out.println("The fairy is delighted with your gift!");
    }
}
