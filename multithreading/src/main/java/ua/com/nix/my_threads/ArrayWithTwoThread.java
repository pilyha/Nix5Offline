package ua.com.nix.my_threads;

import java.util.List;

public class ArrayWithTwoThread implements Runnable {
    private volatile List<Integer> list;
    private Integer counter;
    private Integer firstIndex;
    private Integer lastIndex;


    public ArrayWithTwoThread(List<Integer> list, Integer firstIndex, Integer lastIndex) {
        this.list = list;
        this.counter = 0;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
    }

    @Override
    public void run() {
        for (int i = firstIndex; i < lastIndex; i++) {
            if (checkSimple(i)) {
                counter++;
            }
        }

    }

    private boolean checkSimple(int i) {
        if (i <= 1)
            return false;
        else if (i <= 3)
            return true;
        else if (i % 2 == 0 || i % 3 == 0)
            return false;
        int n = 5;
        while (n * n <= i) {
            if (i % n == 0 || i % (n + 2) == 0)
                return false;
            n = n + 6;
        }
        return true;
    }

    public synchronized Object get() {
        while (counter == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return counter;
    }
}
