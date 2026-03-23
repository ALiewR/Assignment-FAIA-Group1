public class Main {
    public static void main(String[] args) {
        // TEMP
        Printer printer = new Printer();
        BattleContext battleContext = new BattleContext();
        BattleEngine battleEngine = new BattleEngine(battleContext, printer);

        GameCompletion gameCompletion = new GameCompletion(printer);

        // player won, exit game
        while (!battleEngine.executeBattle()) {
            // TEMP
            printer.printLine("You lost!.", true);
            gameCompletion.handleGameResult(false, battleContext);
            return;
            //battleEngine.executeBattle();
        }
        gameCompletion.handleGameResult(true, battleContext);
    }
}
