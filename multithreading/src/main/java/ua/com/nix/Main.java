package ua.com.nix;

import ua.com.nix.my_threads.ArrayWithTwoThread;
import ua.com.nix.my_threads.ReversePrinter;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Queue<Thread> threadList = Collections.asLifoQueue(new ArrayDeque<>());
        for (int i = 0; i < 50; i++) {
            try {
                new ReversePrinter("Hello from thread " + i).start();
                new ReversePrinter("Hello from thread " + i).join();
                threadList.add(new ReversePrinter("Hello from thread " + i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Thread thread : threadList) {
            System.out.println(thread.getName());
        }
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        ArrayWithTwoThread first = new ArrayWithTwoThread(list, 0, list.size() / 2);
        Thread t1 = new Thread(first);
        ArrayWithTwoThread second = new ArrayWithTwoThread(list, (list.size() / 2) + 1, list.size() - 1);
        Thread t2 = new Thread(second);
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
            System.out.println(first.get());
            System.out.println(second.get());
    }
}
