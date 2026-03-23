import java.util.List;

public class UseItem extends Action {
    public Item associatedItem;
    public UseItem(String name, String desc, ACTION_TYPE aType, int numT) {
        super(name, desc, aType, numT);
        if (aType == ACTION_TYPE.USE_SMOKE_BOMB) {
            associatedItem = new Item("smoke bomb");
            targetType = TARGET_TYPE.SELF;
        }
        if (aType == ACTION_TYPE.USE_POTION) {
            associatedItem = new Item("potion");
            targetType = TARGET_TYPE.SELF;
        }
    };

    public void execute(Combatant actor, List<Combatant> targets, BattleContext battleContext) {
        if (actionType == ACTION_TYPE.USE_POTION) {
            for (Combatant eachTarget: targets) {
                eachTarget.oldHP = eachTarget.currentHP;
                eachTarget.currentHP += 100;
                if (eachTarget.currentHP > eachTarget.baseHP) eachTarget.currentHP = eachTarget.baseHP;
            }
        }
    }
}
