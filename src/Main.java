//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    // TEMP
    Printer printer = new Printer();
    BattleContext battleContext = new BattleContext();
    BattleEngine battleEngine = new BattleEngine(battleContext, printer);

    // player won, exit game
    while (!battleEngine.executeBattle()) {
        // TEMP
        printer.printLine("You lost!.", true);
        return;
        //battleEngine.executeBattle();
    }
    // TEMP
    printer.printLine("You win!", true);
}
