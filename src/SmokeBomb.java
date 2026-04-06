public class SmokeBomb extends Item {
    public SmokeBomb() {
        super("Smoke Bomb");
        this.description="Enemy attacks do 0 damage for current turn and next turn";
        this.itemType=ITEM_TYPE.SMOKE_BOMB;
        this.currentDurationLeft=0;
    }
    @Override
    public void use(Combatant user, Combatant target) {
        if(!used) {
            markAsUsed();
            this.currentDurationLeft=this.maxDuration;
        }
    }

}
