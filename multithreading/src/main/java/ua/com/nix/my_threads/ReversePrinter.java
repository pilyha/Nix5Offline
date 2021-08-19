package ua.com.nix.my_threads;

public class ReversePrinter extends Thread {

    public ReversePrinter(int name) {
        super(String.valueOf(name));
    }

    @Override
    public void run() {
        System.out.println("Hello from thread: " + this.getName());
    }
}
