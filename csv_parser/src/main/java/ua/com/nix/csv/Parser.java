package ua.com.nix.csv;

import java.io.*;

public class Parser {
    public static CSV parse(File fileName) {
        CSV data = new CSV();
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String line = reader.readLine();
            while (line != null) {
                data.add(line.split(","));
                line= reader.readLine();
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return data;
    }
}
