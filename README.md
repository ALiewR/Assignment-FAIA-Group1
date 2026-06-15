# Java Turn-Based Battle System

NTU SC2002 OOP Group Assignment (AY25/26 Sem 2) — Assignment-FAIA-Group1

A command-line turn-based combat arena built in Java, demonstrating SOLID and Object-Oriented Design Principles.

---

## How to Run

**Requirements:** Java 17+

```bash
javac *.java
java Main
```

---

## Game Summary

Two players each pick a class (Warrior or Wizard) and fight through waves of enemies. Combatants act in order of speed each round. Defeat all enemies to win; reach 0 HP and you lose.

### Classes

| Class | HP | ATK | DEF | SPD | Special Skill |
|---|---|---|---|---|---|
| Warrior | 260 | 40 | 20 | 30 | Shield Bash — stuns target for 2 turns |
| Wizard | 200 | 50 | 10 | 20 | Arcane Blast — hits all enemies; +10 ATK per kill |

### Enemies

| Enemy | HP | ATK | DEF | SPD |
|---|---|---|---|---|
| Goblin | 55 | 35 | 15 | 25 |
| Wolf | 40 | 45 | 5 | 35 |

### Items (pick 2 at start)

| Item | Effect |
|---|---|
| Potion | Heal 100 HP |
| Smoke Bomb | Enemies deal 0 damage this turn and next |
| Power Stone | Use special skill for free without affecting cooldown |

### Difficulty

| Level | Enemies | Backup Spawn |
|---|---|---|
| Easy | 3 Goblins | — |
| Medium | 1 Goblin + 1 Wolf | 2 Wolves |
| Hard | 2 Goblins | 1 Goblin + 2 Wolves |

---

## Sample Gameplay


## Design

---

## Team

NTU SC2002 — Assignment-FAIA-Group1 | AY2025/26 Semester 2
