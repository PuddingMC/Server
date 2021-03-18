package me.puds.server.api.world;

public class Block {
    private Location location;
    private Material material;
    private Biome biome;

    public Block(Location location, Material material, Biome biome) {
        this.location = location;
        this.material = material;
        this.biome = biome;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Biome getBiome() {
        return biome;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }
}
