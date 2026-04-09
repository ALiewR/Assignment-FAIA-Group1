public class DefenceBoost extends StatusEffect {
    private int defenceIncrease;
    private boolean applied;
    public DefenceBoost() {
        super(STATUS_EFFECT_TYPE.DEFENCE_BOOST,"Defence Boost", 2);
        this.defenceIncrease=10;
        this.applied=false;
    }
    @Override
    public void applyEffect(Combatant target) {
        if(target!=null && !applied) {
            target.defence+=defenceIncrease;
            applied=true;
        }
    }
    @Override
    public void removeEffect(Combatant target) {
        if(target!=null && applied) {
            target.defence-=defenceIncrease;
        }
    }
    public int getDefenceIncrease() {
        return defenceIncrease;
    }

}
