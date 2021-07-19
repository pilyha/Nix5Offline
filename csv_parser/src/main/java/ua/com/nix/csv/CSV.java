package ua.com.nix.csv;
import java.util.ArrayList;
import java.util.List;

public class CSV {

    private List<String[]> data;

    public CSV() {
        this.data = new ArrayList<>();
    }

    public CSV(List<String[]> data) {
        this.data = data;
    }

    public String get(int row, int col) {
        return data.get(row + 1)[col];
    }

    public String get(int row, String header) {
        int col = List.of(this.data.get(0)).indexOf(header);
        if (col == -1) {
            throw new RuntimeException("Field not found");
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