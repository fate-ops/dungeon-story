package ch.bbw;

import java.util.Scanner;

class Player {
    private String name;
    private int health;
    private int maxHealth;
    private int damage;
    private int level;
    private int xp;
    private int maxXp;

    public Player(String name) {
        this.name = name;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.damage = 15;
        this.level = 1;
        this.xp = 0;
        this.maxXp = 50;
    }

    void attack(Enemy enemy) {
        double multiplier = 0.8 + Math.random() * 0.4;
        int actualDamage = (int) Math.round(damage * multiplier);

        enemy.setHealth(enemy.getHealth() - actualDamage);

        System.out.println(this.name + " attacks " + enemy.getName() + " for " + actualDamage + " damage!");
    }

    void heavyAttack(Enemy enemy) {
        double successChance = 0.7;

        if (Math.random() >= successChance) {
            System.out.println(this.name + "'s heavy attack missed!");
            return;
        }

        double multiplier = 1.5 + Math.random() * 0.5;
        int actualDamage = (int) Math.round(damage * multiplier);

        enemy.setHealth(enemy.getHealth() - actualDamage);

        System.out.println(this.name + " performs a heavy attack on " + enemy.getName() + " for " + actualDamage + " damage!");
    }

    void heal() {
        int healAmount = 20;

        if (this.health + healAmount > maxHealth) {
            healAmount = maxHealth - this.health;
        }

        this.health += healAmount;

        System.out.println(this.name + " heals for " + healAmount + " health!");
    }

    int getMaxHealth() {
        return maxHealth;
    }

    int getHealth() {
        return health;
    }

    void setHealth(int health) {
        this.health = Math.max(health, 0);
    }

    String getName() {
        return name;
    }

    void claimXp(int xp) {
        this.xp += xp;

        System.out.println(this.name + " claims " + xp + " XP!");

        if (this.xp >= this.maxXp) {
            this.level++;
            this.xp -= this.maxXp;
            this.maxXp = (int) (this.maxXp * 1.2);

            int oldMaxHealth = this.maxHealth;
            int oldDamage = this.damage;

            this.maxHealth += 15;
            this.damage += 3;
            this.health = this.maxHealth;

            System.out.println();
            System.out.println("LEVEL UP!");
            System.out.println(this.name + " has reached level " + this.level + "!");
            System.out.println("Max HP: " + oldMaxHealth + " -> " + this.maxHealth);
            System.out.println("Damage: " + oldDamage + " -> " + this.damage);
            System.out.println("Health fully restored!");
        }
    }
}

class Enemy {
    private String name;
    private int maxHealth;
    private int health;
    private int damage;
    private int xpReward;

    public Enemy(String name, int maxHealth, int damage, int xpReward) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.damage = damage;
        this.xpReward = xpReward;
    }

    String getName() {
        return name;
    }

    int getHealth() {
        return health;
    }

    void setHealth(int health) {
        this.health = Math.max(health, 0);
    }

    int getMaxHealth() {
        return maxHealth;
    }

    void attack(Player player) {
        double multiplier = 0.8 + Math.random() * 0.4;
        int actualDamage = (int) Math.round(damage * multiplier);

        player.setHealth(player.getHealth() - actualDamage);

        System.out.println(this.name + " attacks " + player.getName() + " for " + actualDamage + " damage!");
    }

    int getXpReward() {
        return xpReward;
    }
}

public class Main {
    static void timeout(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static boolean fight(Player fate, Enemy enemy, Scanner sc) {
        System.out.println("AH! A wild " + enemy.getName() + " appears!");
        timeout(1000);

        System.out.println("Your HP: " + fate.getHealth() + " | " + fate.getMaxHealth());
        System.out.println("Enemy HP: " + enemy.getHealth() + " | " + enemy.getMaxHealth());

        timeout(1500);

        while (enemy.getHealth() > 0 && fate.getHealth() > 0) {
            System.out.println("What will you do?");
            System.out.println("[A] Attack");
            System.out.println("[B] Heavy Attack");
            System.out.println("[C] Heal");

            char choice = Character.toUpperCase(sc.nextLine().charAt(0));

            if (choice == 'A') {
                fate.attack(enemy);
            } else if (choice == 'B') {
                fate.heavyAttack(enemy);
            } else if (choice == 'C') {
                fate.heal();
            } else {
                System.out.println("Invalid choice. Please choose again.");
                continue;
            }

            if (enemy.getHealth() > 0) {
                enemy.attack(fate);
            }

            System.out.println("Your HP: " + fate.getHealth() + " | " + fate.getMaxHealth());
            System.out.println("Enemy HP: " + enemy.getHealth() + " | " + enemy.getMaxHealth());
        }

        if (enemy.getHealth() <= 0) {
            System.out.println("HEY! You have defeated the " + enemy.getName() + "!");
            fate.claimXp(enemy.getXpReward());
            return true;
        } else {
            System.out.println("You have been defeated by the " + enemy.getName() + "...maybe next time?");
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Enemy goblin = new Enemy("Goblin", 30, 5, 10);
        Enemy skeleton = new Enemy("Skeleton", 50, 10, 20);
        Enemy demon = new Enemy("Demon", 75, 15, 25);

        System.out.print("Hello Player. Pick a name for your Hero: ");
        String playerName = sc.nextLine();

        Player fate = new Player(playerName);

        System.out.println("Welcome to the game, " + playerName + ".");

        timeout(1000);

        System.out.println("You walk among the forest. It's windy.");
        timeout(1500);
        System.out.println("Huh?");
        timeout(2000);

        if (!fight(fate, goblin, sc)) {
            return;
        }

        timeout(2000);

        System.out.println("Man what the hell was that.");
        timeout(1500);
        System.out.println("I should probably keep moving..");
        timeout(2000);

        if (!fight(fate, skeleton, sc)) {
            return;
        }

        timeout(2000);

        System.out.println("What the hell are these monsters??");
        timeout(1500);
        System.out.println("I should get out of here quick.");
        timeout(2000);

        if (!fight(fate, demon, sc)) {
            return;
        }

        timeout(2000);

        System.out.println("Alright, this was a tough one..");
        timeout(1500);
        System.out.println("Please let me go now.");

        timeout(2000);

        System.out.println("MORE TO COME SOON. Thanks for trying out the game:)");
    }
}