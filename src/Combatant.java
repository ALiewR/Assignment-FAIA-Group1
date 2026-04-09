import java.util.ArrayList;
import java.util.List;

public abstract class Combatant {
    protected String name;
    protected int currentHP;
    protected int baseHP;
    protected int maxHP;
    protected int oldHP;
    protected int atk;
    protected int oldAtk;
    protected int defence;
    protected int speed;
    protected COMBATANT_TYPE combatantType;
    protected List<Action> availableActions = new ArrayList<>();
    protected List<StatusEffect> afflictedStatusEffects = new ArrayList<>();

    public static final int SKILL_MAX_COOLDOWN = 3;
    protected int skillCooldown = 0;

    public String getName()           { return name; }
    public COMBATANT_TYPE getType()   { return combatantType; }
    public int getCurrentHP()         { return currentHP; }
    public int getMaxHP()             { return maxHP; }
    public int getBaseHP()            { return baseHP; }
    public int getAttack()            { return atk; }
    public int getDefence()           { return defence; }
    public int getSpeed()             { return speed; }
    public int getSkillCooldown()     { return skillCooldown; }
    public int getPreviousHP()        { return oldHP; }
    public int getPreviousAttack()    { return oldAtk; }
    public List<Action> getAvailableActions()             { return availableActions; }
    public List<StatusEffect> getAfflictedStatusEffects() { return afflictedStatusEffects; }

    public boolean isAlive() {
        return currentHP > 0;
    }

    public boolean isStunned() {
        for (StatusEffect effect : afflictedStatusEffects) {
            if (effect.statusEffectType == STATUS_EFFECT_TYPE.STUNNED) return true;
        }
        return false;
    }

    public boolean isSkillReady() {
        return skillCooldown == 0;
    }

    public void takeDamage(int amount) {
        currentHP = Math.max(0, currentHP - amount);
    }

    public void heal(int amount) {
        currentHP = Math.min(currentHP + amount, maxHP);
    }

    public void resetCondition() {
        currentHP = baseHP;
        maxHP = baseHP;
        atk = oldAtk;
        skillCooldown = 0;
        afflictedStatusEffects.clear();
    }

    public void resetCooldown() {
        skillCooldown = SKILL_MAX_COOLDOWN;
    }
    public void depleteCooldown() {
        if (skillCooldown > 0) skillCooldown--;
    }

    public void addStatusEffect(StatusEffect effect) {
        afflictedStatusEffects.add(effect);
    }

    public void applyStatusEffects() {
        for (StatusEffect effect : afflictedStatusEffects) {
            switch (effect.statusEffectType) {
                case STUNNED:
                    // Checked via isStunned(); no per-turn stat change needed
                    break;
                case ARCANE_BOOST:
                    effect.applyEffect(this);
                    break;
                case DEFENCE_BOOST:
                    effect.applyEffect(this);
                    break;
                default:
                    break;
            }
        }
    }

    public List<String> updateStatusEffectDuration() {
        List<String> expiredStatusEffectNames = new ArrayList<>();
        List<StatusEffect> statusEffectsToRemove = new ArrayList<>();
        for (StatusEffect eachStatusEffect: afflictedStatusEffects) {
            eachStatusEffect.decreaseDuration();
            if (eachStatusEffect.isExpired()) {
                expiredStatusEffectNames.add(eachStatusEffect.name);
                statusEffectsToRemove.add(eachStatusEffect);
            }
        }
        // remove expired from afflicted list
        for (StatusEffect eachStatusEffect: statusEffectsToRemove) {
            eachStatusEffect.removeEffect(this);
            afflictedStatusEffects.remove(eachStatusEffect);
        }
        return expiredStatusEffectNames;
    }


    public Action getSpecialSkill() {
        for (Action eachAction: availableActions) {
            if (eachAction.actionType == ACTION_TYPE.SPECIAL_SKILL ||
                    eachAction.actionType == ACTION_TYPE.ARCANE_BLAST) return eachAction;
        }
        return null;
    }

    public List<String> getExpiredEffectNames() {
        List<String> expired = new ArrayList<>();
        for (StatusEffect effect : afflictedStatusEffects) {
            if (effect.currentDuration <= 0) expired.add(effect.name);
        }
        return expired;
    }

    public void savePreviousStats() {
        oldHP = currentHP;
        oldAtk = atk;
    }

    /*public void depleteCooldown() {
        currentSkillMaxCooldown--;
        if (currentSkillMaxCooldown <= 0) currentSkillMaxCooldown = 0;
    }*/
}
