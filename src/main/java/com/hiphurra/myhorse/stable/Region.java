package com.hiphurra.myhorse.stable;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.geom.Point2D;

public class Region {

    private final World world;

    private final double minX;
    private final double minZ;

    private final double maxX;
    private final double maxZ;

    public Region(World world, double minX, double minZ, double maxX, double maxZ) {
        this.world = world;

        this.minX = minX;
        this.minZ = minZ;

        this.maxX = maxX;
        this.maxZ = maxZ;
    }

    public Region(Location loc1, Location loc2) {
        //Assert.assertSame("Both locations have to be in the same world!", loc1.getWorld(), loc2.getWorld());

        this.world = loc1.getWorld();

        this.minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        this.minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

        this.maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        this.maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
    }

    public World getWorld() {
        return world;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public double getMinZ() {
        return minZ;
    }

    public int getWidth() {
        return Math.abs((int) getMaxX() - (int) getMinX());
    }

    public int getHeight() {
        return Math.abs((int) getMaxZ() - (int) getMinZ());
    }

    public boolean isInRegion(@NotNull Location location) {
        World world = location.getWorld();
        if(world == null) return false;
        if(!location.getWorld().equals(getWorld())) return false;

        double locX = location.getBlockX();
        double locZ = location.getBlockZ();

        if(getMinX() <= locX && getMaxX() >= locX) {
            return (getMinZ() <= locZ && getMaxZ() >= locZ);
        }
        return false;
    }

    public boolean isIntersecting(Region region) {
        return intersectsBoundingBox(region);
    }

    private boolean intersectsBoundingBox(Region region) {
        Point2D l1 = new Point2D.Double(getMaxX() + 1, getMinZ() + 1);
        Point2D r1 = new Point2D.Double(getMinX() + 1, getMaxZ() + 1);
        Point2D l2 = new Point2D.Double(region.getMaxX(), region.getMinZ());
        Point2D r2 = new Point2D.Double(region.getMinX(), region.getMaxZ());

        Rectangle rectangle = new Rectangle();
        rectangle.setFrameFromDiagonal(l1, r1);
        Rectangle rectangleTarget = new Rectangle();
        rectangleTarget.setFrameFromDiagonal(l2, r2);

        return rectangle.intersects(rectangleTarget);
    }
}
