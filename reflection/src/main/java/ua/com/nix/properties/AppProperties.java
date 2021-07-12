package ua.com.nix.properties;

import ua.com.nix.Annotation.PropertyKey;

public class AppProperties {

    public String name;
    public int minConnectXions;

    @Override
    public String toString() {
        return "AppProperties{" +
                "name='" + name + '\'' +
                ", minConnectXions=" + minConnectXions +
                ", maxConnections=" + maxConnections +
                '}';
    }

    @PropertyKey("connections.limit")
    public int maxConnections;
}
