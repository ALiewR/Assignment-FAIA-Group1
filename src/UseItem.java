import java.util.List;

public class UseItem extends Action {
    private Item associatedItem;
    public UseItem(Item item)
    {
        this.associatedItem = item;
        String actionName = "Use " + item.getName();
        String actionDesc;
        int numTargets = 1;
        ACTION_TYPE aType;
        switch(item.itemType) {
            case ITEM_TYPE.SMOKE_BOMB: {
                actionDesc = "Enemy attacks do 0 damage in the current turn and the next turn";
                aType = ACTION_TYPE.USE_SMOKE_BOMB;
                numTargets = 10;
                break;
            }
            case ITEM_TYPE.POWER_STONE: {
                actionDesc="Trigger's user's Special Skill without affecting Cooldown";
                aType = ACTION_TYPE.USE_POWER_STONE;
                break;
            }
            case ITEM_TYPE.POTION:
            default: {
                actionDesc="Heals the user by 100 HP!";
                aType=ACTION_TYPE.USE_POTION;
                break;
            }
        }

        super(actionName, actionDesc, aType, numTargets);

        if (item.itemType == ITEM_TYPE.POWER_STONE) {
            this.doesInflictStatusEffectOnTarget = true;
            this.targetType=TARGET_TYPE.ENEMIES;
        }
        else {
            this.targetType=TARGET_TYPE.SELF;
        }
    }
    @Override
    public void execute(Combatant actor, List<Combatant> targets, BattleContext battleContext) {
        Item itemInIventory = associatedItem; //to avoid throwing error. by right, Combatant shouldn't allow action if no such item is available

        for (Item eachItem: battleContext.getUnusedItems()) {
            if (associatedItem.itemType==eachItem.itemType) {
                itemInIventory = eachItem;
                break;
            }
        }

        if (associatedItem.itemType== ITEM_TYPE.POTION) {
            for (Combatant eachTarget: targets) {
                eachTarget.oldHP = eachTarget.currentHP;
                eachTarget.currentHP += 100;
                if (eachTarget.currentHP > eachTarget.baseHP) eachTarget.currentHP = eachTarget.baseHP;
            }
        }
        else if (associatedItem.itemType== ITEM_TYPE.SMOKE_BOMB) {
            battleContext.activateSmokeBomb();
        }
        else if (associatedItem.itemType== ITEM_TYPE.POWER_STONE)
        {
            Action special=actor.getSpecialSkill();
            if (special instanceof SpecialSkill)
            {
                SpecialSkill skill = (SpecialSkill) special;
                skill.execute(actor,targets,battleContext,false);
            }
        }

        itemInIventory.markAsUsed();
    }
    @Override
    public String getName()
    {
        return "Use" + associatedItem.getName();

    }
    public Item getAssociatedItem() { return associatedItem; }
}