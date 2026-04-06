public class PowerStone extends Item {
    public PowerStone() {
        super("Power Stone");
        this.description="Trigger special skill once without affecting cooldown";
        this.itemType=ITEM_TYPE.POWER_STONE;
        this.currentDurationLeft=0;
    }
    @Override
    public void use(Combatant user, Combatant target) {
        if(!used && user!=null) {
            Action specialSkill=user.getSpecialSkill();
            if(specialSkill!=null) {
                specialSkill.execute(user, target);
            }
            markAsUsed();
        }
    }

}
