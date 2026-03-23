public class StatusEffect {
    public STATUS_EFFECT_TYPE statusEffectType = STATUS_EFFECT_TYPE.STUNNED;
    public String name = "Stun";
    public final int maxDuration = 2;
    public int currentDuration = 2;
    public boolean updateDuration() {
        currentDuration--;
        if (currentDuration <= 0) return true;
        return false; // returns if duration expired
    }
}
