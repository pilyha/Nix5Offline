package ua.com.nix;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class Race {
    private final int horseAmount;
    private static CountDownLatch count;
    private static CountDownLatch finishedCount;
    private static ConcurrentHashMap<Integer, Integer> horses = new ConcurrentHashMap<Integer, Integer>();
    private final int usersBet;
    private static int currentHorseId = 1;

    public Race(int horseAmount, int usersBet) {
        this.horseAmount = horseAmount;
        count = new CountDownLatch(this.horseAmount);
        finishedCount = new CountDownLatch(this.horseAmount);
        this.usersBet = usersBet;
    }

    public void start() {
        for (int i = 0; i < horseAmount; i++) {
            new Thread(new Horse()).start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (count.getCount() != 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getResultedPlace() {
        try {
            finishedCount.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return horses.get(usersBet) + 1;
    }

    private static class Horse implements Runnable {

        private final int id;

        public Horse() {
            this.id = currentHorseId++;
        }

        @Override
        public void run() {
            count.countDown();
            try {
                count.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int totalDistance = 0;
            while (totalDistance < 1000) {
                int sleepMax = 500;
                int sleepMin = 400;
                int sleepTime = getRandomInRange(sleepMin, sleepMax);
                int distanceMax = 100;
                int distanceMin = 200;
                int distancePerStep = getRandomInRange(distanceMin, distanceMax);
                if (totalDistance > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (totalDistance >= 800) {
                    int currentDistance = 0;
                    while (totalDistance < 1000 || currentDistance < distancePerStep) {
                        currentDistance++;
                        totalDistance++;
                    }
                } else {
                    totalDistance += distancePerStep;
                }
                System.out.println("Horse_" + this.id + " has already run: " + totalDistance);
            }
            System.out.println("Horse_" + this.id + " has finished race!");
            finishedCount.countDown();
            horses.put(this.id, horses.size());
        }

        private int getRandomInRange(int min, int max) {
            int sleepTimeRange = max - min + 1;
            return (int) (Math.random() * sleepTimeRange) + min;
        }
    }
}
