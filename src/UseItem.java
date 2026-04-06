import java.util.List;

public class UseItem extends Action {
    private Item associatedItem;
    public UseItem(Item item)
    {
        super("Use" + item.getName(), "Uses an item", ACTION_TYPE.USE_POTION, 1);
        this.associatedItem = item;

        if (item.itemType == ITEM_TYPE.SMOKE_BOMB || item.itemType == ITEM_TYPE.POTION) {
            this.targetType=TARGET_TYPE.SELF;
        }
        else{
            this.targetType=TARGET_TYPE.ENEMIES;
        }
    }
    @Override
    public void execute(Combatant actor, List<Combatant> targets, BattleContext battleContext) {
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
                if (special != null)
                {
                    special.execute(actor,targets,battleContext);
                }
            }
            associatedItem.use();
                }
                @Override
                public String getName()
                {
                    return "Use" + associatedItem.getName();

                }
            }