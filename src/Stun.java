public class Stun extends StatusEffect {
    public Stun() {
        super(STATUS_EFFECT_TYPE.STUNNED,"Stun", 2);
    }
    @Override
    public void applyEffect(Combatant target) {
        // if(target!=null) {
                //target.setStunned(true);
        //}
    }
    @Override
    public void removeEffect(Combatant target) {
        // if(target!=null) {
                //target.setStunned(false);
            //}

    }
}
