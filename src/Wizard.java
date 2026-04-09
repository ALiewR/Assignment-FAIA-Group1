public class Wizard extends Player {
    public Wizard() {
        super("Wizard",
                200,
                200,
                200,
                50,
                10,
                20);

        availableActions.add(new ArcaneBlast());
    }
    public Wizard(String identifier) {
        super("Wizard " + identifier,
                200,
                200,
                200,
                50,
                10,
                20);

        availableActions.add(new ArcaneBlast());
    }
}
