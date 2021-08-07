package ua.com.nix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public void runApp(){
        try {
            System.out.println("Enter number of horses in race: ");
            int amount = Integer.parseInt(reader.readLine());
            System.out.println("Enter your horse to bet: ");
            int choice = Integer.parseInt(reader.readLine());
            System.out.println("You've bet on horse with number " + choice);
            Race race = new Race(amount, choice);
            System.out.println("Race started");
            race.start();
            System.out.println("Your horse is in " + race.getResultedPlace() + " place");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
