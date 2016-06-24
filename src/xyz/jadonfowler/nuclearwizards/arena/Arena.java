package xyz.jadonfowler.nuclearwizards.arena;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Arena {

    private int id; // putting names in maps instead of arenas
    private List<WorldMap> maps;
    private Map<UUID, PlayerTeam> players;
    private WorldMap currentMap;
    private static Random random = new Random();

    public Arena(int id, List<WorldMap> maps) {
        this.id = id;
        this.maps = maps;
        if (maps.size() <= 1)
            throw new IllegalArgumentException("Not enough maps!");
        this.players = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public WorldMap getCurrentMap() {
        return currentMap;
    }

    /**
     * Adds a player to the Arena
     * 
     * @return if the addition was successful
     */
    public boolean addPlayer(Player player) {
        players.put(player.getUniqueId(), PlayerTeam.SPECTATOR);
        player.teleport(currentMap.getLobby());
        setTeam(player, PlayerTeam.SPECTATOR);
        return false;
    }

    /**
     * Get a random unloaded map.
     * 
     * @return random unloaded map
     */
    public WorldMap getRandomMap() {
        WorldMap wm = maps.get(random.nextInt(maps.size()));
        if (wm.isLoaded())
            return getRandomMap();
        return wm;
    }

    /**
     * Get a random map, teleport all players to that map, make all players
     * spectators, and unload the old map.
     */
    public void switchMaps() {
        WorldMap newMap = getRandomMap();
        newMap.loadWorld();
        for (UUID u : players.keySet()) {
            Player p = Bukkit.getPlayer(u);
            setTeam(p, PlayerTeam.SPECTATOR);
            p.teleport(newMap.getLobby());
        }
        currentMap.unloadWorld();
        currentMap = newMap;
    }

    private void setTeam(Player player, PlayerTeam team) {
        players.put(player.getUniqueId(), team);
        player.setHealth(player.getMaxHealth());
        switch (team) {
            case HERO:
                player.setGameMode(GameMode.SURVIVAL);
                break;
            case VILLAIN:
                player.setGameMode(GameMode.SURVIVAL);
                break;
            case SPECTATOR:
                player.setGameMode(GameMode.SPECTATOR);
                break;
        }
    }

    /**
     * The team a player is on.
     */
    public static enum PlayerTeam {
        HERO, VILLAIN, SPECTATOR;
    }

    /**
     * The state of an arena.
     */
    public static enum ArenaState {

        IDLE("Waiting for Players!"),
        ABOUT_TO_START("About to start!"),
        IN_PROGRESS("In progress!"),
        FINISHED("Just ended!");

        private String name;

        ArenaState(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

}
