public class Warrior extends Player {
    public Warrior() {
        super("Warrior",
                260,
                260,
                260,
                40,
                20,
                30);

        availableActions.add(new ShieldBash());
    }
    public Warrior(String identifier) {
        super("Warrior " + identifier,
                260,
                260,
                260,
                40,
                20,
                30);

        availableActions.add(new ShieldBash());
    }

}
