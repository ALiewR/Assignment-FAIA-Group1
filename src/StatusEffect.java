public class StatusEffect {
    public STATUS_EFFECT_TYPE statusEffectType;
    public String name;
    public int maxDuration;
    public int currentDuration;
    public StatusEffect(STATUS_EFFECT_TYPE statusEffectType, String name, int maxDuration) {
        this.statusEffectType=statusEffectType;
        this.name=name;
        this.maxDuration=maxDuration;
    }
    public STATUS_EFFECT_TYPE getStatusEffectType() {
        return statusEffectType;
    }
    public String getName() {
        return name;
    }
    public int getMaxDuration() {
        return maxDuration;
    }
    public int getCurrentDuration() {
        return currentDuration;
    }
    public void decreaseDuration() {
        if (currentDuration>0) {
            currentDuration--;
        }
    }
    public boolean isExpired() {
        return currentDuration<=0;
    }
    public void resetDuration() {
        currentDuration=maxDuration;
    }
    //public abstract void applyEffect(Combatant target);
    //public abstract void removeEffect(Combatant target);



    
}
