public class UseItem extends Action {
    public Item associatedItem;
    public UseItem(String name, String desc, ACTION_TYPE aType, int numT) {
        super(name, desc, aType, numT);
        if (aType == ACTION_TYPE.USE_SMOKE_BOMB) {
            associatedItem = new Item("smoke bomb");
        }
    };

}
