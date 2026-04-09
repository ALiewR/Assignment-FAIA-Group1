public class Goblin extends Enemy {
    public Goblin() {
        super("Goblin",
                55,
                55,
                55,
                35,
                15,
                25);
    }
    public Goblin(String identifier) {
        super("Goblin " + identifier,
                55,
                55,
                55,
                35,
                15,
                25);
    }
}
