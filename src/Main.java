public class Main {
    public static void main(String[] args) {
        // TEMP
        Printer printer = new Printer();
        BattleContext battleContext = new BattleContext();
        BattleEngine battleEngine = new BattleEngine(battleContext, printer);

        GameCompletion gameCompletion = new GameCompletion(printer);

        // player won, exit game
        boolean isGameWon = battleEngine.executeBattle();
        while (!isGameWon) {
            // TEMP
            printer.printLine("You lost!.", true);
            NEXT_GAME_OPTION_TYPE nextGameOption = gameCompletion.handleGameResult(false, battleContext);
            switch(nextGameOption) {
                case EXIT: return;
                case REPLAY: {
                    isGameWon = battleEngine.executeBattle();
                    break;
                }
                case START_NEW: {
                    // TODO: go back to LoadingScreen
                    break;
                }
            }
        }
        gameCompletion.handleGameResult(true, battleContext);
    }
}
