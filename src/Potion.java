public class Potion extends Item {
    private static final int HEAL_AMOUNT=100;
    public Potion() {
        super("Potion");
        this.description="Heal 100 HP";
        this.itemType=ITEM_TYPE.POTION;
        this.currentDurationLeft=0;
    }
    @Override
    public void use(Combatant user, Combatant target) {
        if(!used && target!=null) {
            target.currentHP=Math.min(target.baseHP, target.currentHP+HEAL_AMOUNT);
            markAsUsed();
        }
    }
}
    
