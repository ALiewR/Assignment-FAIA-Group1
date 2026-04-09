public class Wolf extends Enemy {
    public Wolf() {
        super("Wolf",
                40,
                40,
                40,
                45,
                5,
                35);
    }
    public Wolf(String identifier) {
        super("Wolf " + identifier,
                40,
                40,
                40,
                45,
                5,
                35);
    }
}
