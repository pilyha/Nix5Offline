package ua.com.nix.csv;

import java.util.ArrayList;
import java.util.List;

public class CSV {

    private final List<String[]> data;

    public CSV() {
        this.data = new ArrayList<>();

    }

    public String get(int row, int col) {

        return data.get(row + 1)[col];
    }

    public String get(int row, String header) {
        int col = -1;
        String[] headers = getHeader();
        for (int i = 0; i < headers.length; i++) {
            if(headers[i].equals(header)) {
                col = i;
                break;
            }
        }
        if(col == -1)
        {
            return null;
        }
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