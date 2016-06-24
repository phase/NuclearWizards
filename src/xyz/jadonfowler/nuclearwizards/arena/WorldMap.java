package xyz.jadonfowler.nuclearwizards.arena;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class WorldMap {

    private boolean isLoaded;
    private String name;
    private String creator;
    private String worldName;
    private Location lobby;
    private Location heroSpawn;
    private Location heroCore;
    private Location villainSpawn;
    private Location villainCore;

    public WorldMap(String name, String creator, String worldName, Location lobby, Location heroSpawn, Location villainSpawn) {
        this.name = name;
        this.creator = creator;
        this.worldName = worldName;
        this.lobby = lobby;
        this.heroSpawn = heroSpawn;
        this.villainSpawn = villainSpawn;
        this.isLoaded = false;
    }

    public void loadWorld() {
        if (isLoaded)
            unloadWorld();
        Bukkit.createWorld(new WorldCreator(worldName));
        isLoaded = true;
    }

    /**
     * Unloads world, but doesn't save any changes. This allows for players to
     * destroy terrain and it won't be saved.
     */
    public void unloadWorld() {
        if (!isLoaded)
            return;
        Bukkit.unloadWorld(worldName, false);
        isLoaded = false;
    }

    @Nullable
    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    public String getWorldName() {
        return worldName;
    }

    public Location getLobby() {
        return lobby;
    }

    public Location getHeroSpawn() {
        return heroSpawn;
    }

    public Location getHeroCore() {
        return heroCore;
    }

    public Location getVillainSpawn() {
        return villainSpawn;
    }

    public Location getVillainCore() {
        return villainCore;
    }

}
