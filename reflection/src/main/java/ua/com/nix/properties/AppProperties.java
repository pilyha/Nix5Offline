package ua.com.nix.properties;

import ua.com.nix.annotation.PropertyKey;

public class AppProperties {

    @PropertyKey("connections.name")
    public String name;
    @PropertyKey("connections.speed")
    public double speed;
    @PropertyKey("connections.min")
    public int minConnectXions;
    @PropertyKey("connections.limit")
    public int maxConnections;
    @PropertyKey("isConnect")
    public boolean isConnect;

    @Override
    public String toString() {
        return "AppProperties{" +
                "name='" + name + '\'' +
                ", speed=" + speed +
                ", minConnectXions=" + minConnectXions +
                ", maxConnections=" + maxConnections +
                ", isConnect=" + isConnect +
                '}';
    }
}
