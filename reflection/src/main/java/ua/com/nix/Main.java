package ua.com.nix;

import ua.com.nix.properties.AppProperties;

public class Main {
    public static void main(String[] args) {
        AppProperties appProperties = Solution.run("/app.properties", AppProperties.class);
        System.out.println("appProperties = " + appProperties);
    }
}
