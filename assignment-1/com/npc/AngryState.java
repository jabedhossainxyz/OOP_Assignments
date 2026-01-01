package com.npc;

public class AngryState implements State {
    @Override
    public void talk(Fairy fairy) {
        System.out.println("The fairy glares at you and refuses to talk.");
    }

    @Override
    public void flattery(Fairy fairy) {
        System.out.println("The fairy isn't impressed, but she calms down a little.");
        fairy.setState(new SadState());
    }

    @Override
    public void curse(Fairy fairy) {
        System.out.println("The fairy is furious! She attacks you with magic!");
    }

    @Override
    public void exchange(Fairy fairy) {
        System.out.println("The fairy is in a bad mood. She confiscated your equipment.");
    }

    @Override
    public void giveGift(Fairy fairy) {
        System.out.println("The fairy accepts the gift and her mood improves slightly.");
        fairy.setState(new HappyState());
    }
}
