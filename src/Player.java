public class Player extends Combatant {
    public Player(String name, int baseHP, int currentHP, int maxHP,
                  int atk, int defence, int speed) {
        super(name, baseHP, currentHP, maxHP, atk, defence, speed);
        combatantType = COMBATANT_TYPE.PLAYER;

        availableActions.add(new BasicAttack());
        availableActions.add(new Defend());
    }

    public void addItems(java.util.List<Item> chosenItems) {
        for (Item item : chosenItems) {
            switch (item.itemType) {
                case POTION:
                    availableActions.add(new UseItem(new Potion()));
                    break;
                case SMOKE_BOMB:
                    availableActions.add(new UseItem(new SmokeBomb()));
                    break;
                case POWER_STONE:
                    availableActions.add(new UseItem(new PowerStone()));
                    break;
            }
        }
    }
}
