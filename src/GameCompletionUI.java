import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameCompletionUI extends UI {
    public GameCompletionUI(Printer printer) {
        super(printer);
    }

    public void displayBattleVerdict(boolean isVictory, List<Combatant> players, List<String> enemiesLeft, int roundCount, List<Item> items) {
        // print result
        printBattleSummary(isVictory);

        // print player remaining HP
        if (isVictory) {
            displayMessage("| Remaining HP: ", true);
            for (int i = 0; i < players.size(); i++) {
                if (i > 0) displayMessage("| "); // not first player
                printRemainingHP(players.get(i).currentHP, players.get(i).baseHP);
            }
        }
        // print remaining enemies
        else {
            printEnemiesRemaining(enemiesLeft);
        }

        // print total rounds
        printTotalRounds(roundCount, isVictory);

        // print remaining items
        for (Map.Entry<String, Integer> entry: mapItemsToQuantity(items).entrySet()) {
            printRemainingItem(entry.getKey(), entry.getValue());
        }

        displayLineMessage("");
    }

    /*
    * used to build displayBattleVerdict
    */
    private void printBattleSummary(boolean isVictory) {
        if (isVictory) {
            displayLineMessage("Victory", true);
            displayLineMessage("");
            displayMessage("Result: ", true);
            displayMessage("Player Victory ");
        }
        else {
            displayLineMessage("Defeat", true);
            displayLineMessage("");
            displayMessage("Result: ", true);
            displayMessage("Enemy Victory ");
        }
    }
    private void printRemainingHP(int remainingHP, int baseHP) {
        displayMessage(remainingHP + "/" + baseHP + " ");
    }
    private void printTotalRounds(int rounds, boolean isVictory) {
        displayMessage("| Total Rounds", true);
        if (!isVictory) displayMessage(" Survived: ", true);
        else displayMessage(": ", true);
        displayMessage(rounds + " ");
    }
    private void printEnemiesRemaining(List<String> remainingEnemyNames) {
        displayMessage("| Enemies remaining: ", true);
        displayMessage(remainingEnemyNames.size() + ": ");
        for (int i = 0; i < remainingEnemyNames.size(); i++) {
            if (i > 0) displayMessage(", "); //if not first
            displayMessage(remainingEnemyNames.get(i));
        }
        displayMessage(" ");
    }
    private Map<String, Integer> mapItemsToQuantity(List<Item> items) {
        Map<String, Integer> nameCountMap = new HashMap<>();
        for (int i = 0; i < items.size(); i++) {
            // put into a dict of item: quantity then individually print
            // TODO: adjust according to accessibility in Item class
            if (!(nameCountMap.containsKey(items.get(i).name))) {
                int quantity = 0;
                if (!(items.get(i).getIsUsed())) quantity = 1; // not yet used so should be 1
                nameCountMap.put(items.get(i).name, quantity);
            }
            else { // add to count for said item
                nameCountMap.replace(items.get(i).name, nameCountMap.get(items.get(i).name) + 1);
            }
        }
        return nameCountMap;
    }
    private void printRemainingItem(String itemName, int itemCount) {
        displayMessage("| Remaining " + itemName + ": ", true);
        displayMessage(itemCount + " ");
        if (itemCount > 0) displayMessage("<-- unused ");
    }

    /*
    * used to get player to choose next opion
    */
    public NEXT_GAME_OPTION_TYPE selectNextGameOption() {
        int userChoice = -1;

        while (userChoice < 0 || userChoice > 2) {
            displayLineMessage("Would you like to...", true);
            displayLineMessage("0: Exit");
            displayLineMessage("1: Replay with Same Settings");
            displayLineMessage("2: Start a New Game (return to home screen)");
            displayLineMessage("Select your choice: ", true);
            userChoice = getUIInt();

            switch (userChoice) {
                case 0: return NEXT_GAME_OPTION_TYPE.EXIT;
                case 1: return NEXT_GAME_OPTION_TYPE.REPLAY;
                case 2: return NEXT_GAME_OPTION_TYPE.START_NEW;
                default: {
                    displayLineMessage("Sorry, that's not one of the options. Try keying in a number corresponding to the options mentioned!");
                }
            }
        }
        return NEXT_GAME_OPTION_TYPE.EXIT;
    }
}
