package ua.com.nix.csv;

import java.util.*;

public class CSV {

    private final List<String[]> data;
    private final List<String> header;
    private Map<Integer,String> headers;

    public CSV() {
        this.data = new ArrayList<>();
        header = Arrays.asList(data.get(0));

        for (int i = 0; i < header.size(); i++) {
            headers.put(i,header.get(i));
        }
        System.out.println(headers);

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