import java.util.ArrayList;
import java.util.List;

public abstract class Combatant {
    public String name;
    public int oldHP = 10;
    public int currentHP = 10;
    public int baseHP = 10;
    public int atk = 10;
    public int defence = 5;
    public COMBATANT_TYPE combatantType;
    public List<Action> availableActions = new ArrayList<>();
    public List<StatusEffect> afflictedStatusEffects = new ArrayList<>();
    public void resetCondition() {
        currentHP = baseHP;
    }
    public final int specialSkillMaxCooldown = 2;
    public int currentSkillMaxCooldown = 2;
    public void depleteCooldown() {
        currentSkillMaxCooldown--;
        if (currentSkillMaxCooldown <= 0) currentSkillMaxCooldown = 0;
    }
    public List<String> updateStatusEffectDuration() {
        List<String> expiredStatusEffectNames = new ArrayList<>();
        List<StatusEffect> statusEffectsToRemove = new ArrayList<>();
        for (StatusEffect eachStatusEffect: afflictedStatusEffects) {
            if (eachStatusEffect.updateDuration()) {
                expiredStatusEffectNames.add(eachStatusEffect.name);
                statusEffectsToRemove.add(eachStatusEffect);
            }
        }
        // remove expired from afflicted list
        for (StatusEffect eachStatusEffect: statusEffectsToRemove) {
            afflictedStatusEffects.remove(eachStatusEffect);
        }
        return expiredStatusEffectNames;
    }
    public boolean isStunned() {
        for (StatusEffect eachStatusEffect: afflictedStatusEffects) {
            if (eachStatusEffect.statusEffectType == STATUS_EFFECT_TYPE.STUNNED) return true;
        }
        return false;
    }
    public Action getSpecialSkill() {
        for (Action eachAction: availableActions) {
            if (eachAction.actionType == ACTION_TYPE.SPECIAL_SKILL) return eachAction;
        }
        return new Action();
    }
}
