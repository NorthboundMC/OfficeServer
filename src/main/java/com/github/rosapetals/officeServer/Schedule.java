package com.github.rosapetals.officeServer;

import org.bukkit.Bukkit;

import java.util.*;


public class Schedule {
    public static void schedule() {

        Timer timer = new Timer();
        List<String> list = Arrays.asList("banana", "apples", "pickles", "cucumber");
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        String randomItem = list.get(randomIndex);


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("ATTENTION:" + randomItem);
            }
        };

        timer.scheduleAtFixedRate(task, 0, 30000);
    }
}