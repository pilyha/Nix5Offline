package ua.com.nix;

import ua.com.nix.my_threads.ArrayWithTwoThread;
import ua.com.nix.my_threads.ReversePrinter;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Solution {
    public void run() {
        System.out.println("************ First task ***********");
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            threadList.add(new ReversePrinter(i));
        }
        for (int i = threadList.size() - 1; i >= 0; i--) {
            threadList.get(i).start();
            try {
                threadList.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        System.out.println("************ Second task ***********");
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        Callable<Integer> first = new ArrayWithTwoThread(list, 0, list.size() / 2);
        Callable<Integer> second = new ArrayWithTwoThread(list, (list.size() / 2) + 1, list.size() - 1);
        FutureTask<Integer> fFutureTask = new FutureTask<>(first);
        FutureTask<Integer> sFutureTask = new FutureTask<>(second);
        try {
            System.out.println("The first thread counted: " + fFutureTask.get());
            System.out.println("The second thread counted: " + sFutureTask.get());
            int primeCounter = (Integer) fFutureTask.get() + (Integer) sFutureTask.get();
            System.out.println("Count primary number in list: " + primeCounter);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
