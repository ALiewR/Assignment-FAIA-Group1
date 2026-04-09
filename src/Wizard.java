public class Wizard extends Player {
    public Wizard() {
        name = "Wizard";
        baseHP = 200;
        currentHP = 200;
        maxHP = 200;
        atk = 50;
        oldAtk = atk;
        defence = 10;
        speed = 20;

        availableActions.add(new ArcaneBlast());
    }
    public Wizard(String identifier) {
        name = "Wizard " + identifier;
        baseHP = 200;
        currentHP = 200;
        maxHP = 200;
        atk = 50;
        oldAtk = atk;
        defence = 10;
        speed = 20;

        availableActions.add(new ArcaneBlast());
    }
}
