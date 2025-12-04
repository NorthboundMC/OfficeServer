package com.github.rosapetals.officeServer.features;

import org.bukkit.Location;

import java.util.UUID;

public class LaundryUnit {


    private final UUID owner;
    private final Location washerLocation;
    private final Location dryerLocation;


    public LaundryUnit(UUID owner, Location washerLocation, Location dryerLocation) {
        this.owner = owner;
        this.washerLocation = washerLocation;
        this.dryerLocation = dryerLocation;
    }

    public UUID getOwner() { return owner; }
    public Location getWasherLocation() { return washerLocation; }
    public Location getDryerLocation() { return dryerLocation; }
}
