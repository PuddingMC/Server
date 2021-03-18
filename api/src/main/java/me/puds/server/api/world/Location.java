package me.puds.server.api.world;

import me.puds.server.api.protocol.Vector;

public class Location {
    private Schematic schematic;

    private double x;
    private double y;
    private double z;

    private float pitch;
    private float yaw;

    public Location(Schematic schematic, double x, double y, double z, float pitch, float yaw) {
        this.schematic = schematic;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public Location(Schematic schematic, double x, double y, double z) {
        this.schematic = schematic;
        this.x = x;
        this.y = y;
        this.z = z;
        pitch = 0;
        yaw = 0;
    }

    public Location(Schematic schematic, Vector position, float pitch, float yaw) {
        this.schematic = schematic;
        x = position.getX();
        y = position.getY();
        z = position.getZ();
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public Location(Schematic schematic, Vector position) {
        this.schematic = schematic;
        x = position.getX();
        y = position.getY();
        z = position.getZ();
        pitch = 0;
        yaw = 0;
    }

    public Schematic getSchematic() {
        return schematic;
    }

    public void setSchematic(Schematic schematic) {
        this.schematic = schematic;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}
