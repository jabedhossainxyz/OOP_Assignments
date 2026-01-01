package com.npc;

public class SadState implements State {
    @Override
    public void talk(Fairy fairy) {
        System.out.println("The fairy sighs and doesn't want to say much.");
    }

    @Override
    public void flattery(Fairy fairy) {
        System.out.println("The fairy feels a bit better thanks to your kind words.");
        fairy.setState(new HappyState());
    }

    @Override
    public void curse(Fairy fairy) {
        System.out.println("The fairy bursts into tears and gets angry at your cruelty.");
        fairy.setState(new AngryState());
    }

    @Override
    public void exchange(Fairy fairy) {
        System.out.println("The fairy is too sad to trade right now.");
    }

    @Override
    public void giveGift(Fairy fairy) {
        System.out.println("The fairy wipes her tears and smiles. She appreciates the gift.");
        fairy.setState(new HappyState());
    }
}
