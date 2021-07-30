package ua.com.nix.csv;

import java.util.*;

public class CSV {

    private final List<String[]> data;
    private final List<String[]> header;
    private final Map<String,Integer> headers;

    public CSV() {
        this.data = new ArrayList<>();
        this.header = new ArrayList<>();
        this.headers = new HashMap<>();
    }

    public String get(int row, int col) {

        return data.get(row + 1)[col];
    }

    public String get(int row, String header) {
        String[] headers = getHeader();
        for (int i = 0; i < headers.length; i++) {
            this.headers.put(headers[i], i);
        }
        int col = this.headers.get(header);
        return data.get(row + 1)[col];
    }

    public String[] getHeader() {
       return this.data.get(0);
    }

    public void add(String[] row) {
        this.data.add(row);
    }

    public int size() {
        return data.size() - 1;
    }
}