import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleEngineUI extends UI {
    public BattleEngineUI(Printer printer) {
        super(printer);
    }

    /*
    * called by BattleEngine
    * BattleEngine decides WHEN the message is needed.
    * BattleEngineUI decides HOW the message is formatted
    */
    public void displayRoundHeader(int round) {
        displayLineMessage("Round " + round, true);
    }
    public Action selectAction(Combatant player) {
        int userChoice = -1;

        while (userChoice < 0 || userChoice >= player.availableActions.size()) {
            // display info for user to know
            displayLineMessage(player.name + " can perform one of the following actions...");
            for (int i = 0; i < player.availableActions.size(); i++) {
                displayLineMessage(i + ": " + player.availableActions.get(i).name + " - " + player.availableActions.get(i).description);
            }
            displayMessage("Select action you wish to take: ", true);
            userChoice = getUIInt();
        }

        displayLineMessage(player.availableActions.get(userChoice).name + " can be used on "
                + player.availableActions.get(userChoice).numOfTargets + " target(s).");

        // input is valid
        return player.availableActions.get(userChoice);
    }
    public void displayTurnStunned(String combatantName) {
        displayMessage(combatantName + " --> STUNNED: ", true);
        displayMessage("Turn skipped ");
    }
    public void displayTurnEliminated(String combatantName) {
        displayMessage(combatantName + " --> ELIMINATED: ", true);
        displayMessage("Skipped ");
    }
    public Combatant selectTarget(String actionName, List<Combatant> possibleTargets) {
        int userChoice = -1;

        while (userChoice < 0 || userChoice >= possibleTargets.size()) {
            // display info for user to know
            displayLineMessage(actionName + " can be used on...");
            for (int i = 0; i < possibleTargets.size(); i ++) {
                displayLineMessage(i + ": " + possibleTargets.get(i).name);
            }
            displayMessage("Select whom you wish to target: ", true);
            userChoice = getUIInt();
        }

        // input is valid
        return possibleTargets.get(userChoice);
    }
    public void displayTurnOutcome(Combatant actor, Action action, List<Combatant> targets,
                                   boolean hasInflictStatusEffectOnTargetThisTurn) {
        // TODO: adjust based on how to get each info needed from each class
        // whose turn is this
        displayMessage(actor.name + " --> ", true);
        // TODO: adjust how to get action type based on how its implemented
        switch(action.actionType) {
            case ATTACK:
            case SPECIAL_SKILL: { // TODO: print info of ATK bonus if ArcaneBlast used
                printAttacking(action.name);
                for (int i = 0; i < targets.size(); i++) {
                    if (i > 0) displayMessage("| "); // if not first target hit
                    if (hasInflictStatusEffectOnTargetThisTurn)
                        if (targets.get(i).afflictedStatusEffects.size() <= 0) {

                            printTargetImpact(targets.get(i).name, targets.get(i).currentHP,
                                    actor.atk, targets.get(i).defence, (targets.size() == 1),
                                    null);
                        }
                        else {
                            printTargetImpact(targets.get(i).name, targets.get(i).currentHP,
                                    actor.atk, targets.get(i).defence, (targets.size() == 1),
                                    targets.get(i).afflictedStatusEffects.get(targets.get(i).afflictedStatusEffects.size() - 1)); // last status effect taken as most recently inflicted
                        }
                    else
                        printTargetImpact(targets.get(i).name, targets.get(i).currentHP,
                                actor.atk, targets.get(i).defence, (targets.size() == 1));
                }
                break;
            }
            case USE_ITEM: {
                // TODO
                break;
            }
            case USE_POTION: {
                // TODO
                break;
            }
        }
    }
    public void displayTurnOutcomeSmokeBomb(Combatant actor, Action action, List<Combatant> targets) {
        // TODO
    }
    public void displayBackupSpawnedInfo(List<Combatant> backupEnemies) {
        displayMessage("All initial enemies eliminated --> ");
        displayMessage("Backup Spawn triggered! ", true);
        for (int i = 0; i < backupEnemies.size(); i++) {
            if (i > 0) displayMessage("+ "); // not first enemy spawned
            // TODO: adjust according to accessibility in Combatant class
            displayMessage(backupEnemies.get(i).name + " (HP:" +
                    backupEnemies.get(i).currentHP + ") ");
        }
        displayLineMessage("enter simultaneously");
    }
    public void displayEndOfRoundInfo(BattleContext battleContext, int roundNum, int specialSkillsCooldown) {
        displayMessage("End of Round " + roundNum + ": ", true);

        // display players & enemies info
        printCombatantsCondition(battleContext.getPlayers(), battleContext.getEnemies());

        // display item info
        printItems(battleContext.getItems());

        // display cooldown info
        displayLineMessage("| Special Skills Cooldown: " + specialSkillsCooldown + " rounds ");

        // print round divider
        displayLineMessage("----------------------------------------", true);
    }
    private void printAttacking(String attackName) {
        displayMessage(attackName + " --> ", true);
    }
    // used for impact of ATTACKS
    private void printTargetImpact(String targetName, int currentHP,
                                   int attackerAtk, int targetDef, boolean isOnlyTarget) {
        displayMessage(targetName + ": ", true);
        int damageDealt = attackerAtk - targetDef;
        displayMessage("HP: " + (currentHP + damageDealt) + " --> " + currentHP + " ");
        // if target eliminated, print extra tag
        if (currentHP <= 0) displayMessage("X ELIMINATED ");
        displayMessage("(dmg: " + attackerAtk + "-" + targetDef + "=" + damageDealt + ") ");
        // if not the only target, print extra to acknowledge that this target is still alive
        if (currentHP > 0 && !isOnlyTarget)
            displayMessage("| " + targetName + " survives ");
    }
    // if inflicts status effect on target this turn
    private void printTargetImpact(String targetName, int currentHP,
                                   int attackerAtk, int targetDef, boolean isOnlyTarget,
                                   StatusEffect statusEffectAfflicted) {
        printTargetImpact(targetName, currentHP, attackerAtk, targetDef, isOnlyTarget);
        displayMessage("| " + targetName + " ");
        if (statusEffectAfflicted.statusEffectType == STATUS_EFFECT_TYPE.STUNNED) {
            displayMessage("STUNNED ");
        }
        // print duration
        displayMessage("(" + statusEffectAfflicted.maxDuration + " turns) ");
    }
    private void printCombatantsCondition(List<Combatant> players, List<Combatant> enemies) {
        // print players
        for (int i = 0; i < players.size(); i++) {
            if (i > 0) displayMessage("| "); // if not first player
            // TODO: adjust according to accessibility in Combatant class
            printPlayerCondition(players.get(i).name, players.get(i).currentHP, players.get(i).baseHP);
            if (players.get(i).isStunned()) printStunnedModified();
        }
        // print enemies
        for (Combatant eachEnemy: enemies) {
            // TODO: adjust according to accessibility in Combatant class
            printEnemyCondition(eachEnemy.name, eachEnemy.currentHP);
            if (eachEnemy.isStunned()) printStunnedModified();
        }
    }
    private void printPlayerCondition(String name, int currentHP, int baseHP) {
        displayMessage(name + " HP: " + currentHP + "/" + baseHP + " ");
    }
    private void printEnemyCondition(String name, int currentHP) {
        displayMessage("| " + name + " HP: " + currentHP + " ");
    }
    private void printStunnedModified() {
        displayMessage("[STUNNED] ");
    }
    private void printItems(List<Item> items) {
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

        // print all items
        for (Map.Entry<String, Integer> entry: nameCountMap.entrySet()) {
            printItem(entry.getKey(), entry.getValue());
        }
    }
    private void printItem(String itemName, int itemCount) {
        displayMessage("| " + itemName + ": " + itemCount + " ");
    }
    public void printStatusEffectExpires(String statusEffectName) {
        displayMessage("| " + statusEffectName + " expires ");
    }
    public void printSpecialSkillCooldown(int newCooldown) {
        displayMessage("| Cooldown set to " + newCooldown + " ");
    }
}
