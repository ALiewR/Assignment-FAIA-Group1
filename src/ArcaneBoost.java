public class ArcaneBoost extends StatusEffect {
    private int attackIncrease;
    public ArcaneBoost() {
        super(STATUS_EFFECT_TYPE.ARCANE_BOOST, "Arcane Boost", 999);  
        this.attackIncrease=10;
    }
    @Override
    public void applyEffect(Combatant target) {
        if(target!=null) {
            target.atk+=attackIncrease;
        }
    }
    @Override
    public void removeEffect(Combatant target) {
        if(target!=null) {
            target.atk-=attackIncrease;
        }
    }
    public int getAttackIncrease() {
        return attackIncrease;
    }

}


