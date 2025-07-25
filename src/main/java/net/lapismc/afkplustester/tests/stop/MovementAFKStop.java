package net.lapismc.afkplustester.tests.stop;

import net.lapismc.afkplus.playerdata.AFKPlusPlayer;
import net.lapismc.afkplustester.tests.AFKTest;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MovementAFKStop implements AFKTest {

    boolean failedSetup = false;
    Location loc;
    AFKPlusPlayer player;

    @Override
    public void setup(AFKPlusPlayer player) {
        this.player = player;
        //Set the player as AFK
        player.forceStartAFK();
        //Check they are AFK
        if (!player.isAFK())
            failedSetup = true;
        //Get the Bukkit Player
        Player p = Bukkit.getPlayer(player.getUUID());
        //Store their location so we can teleport them back afterwards
        loc = p.getLocation();
        //Move the player to make them leave AFK
        p.setVelocity(new Vector(1, 1, 1));
    }

    @Override
    public boolean check() {
        return !player.isAFK();
    }

    @Override
    public void revert() {
        player.interact();
        Bukkit.getPlayer(player.getUUID()).teleport(loc);
    }

    @Override
    public String getTestName() {
        return "Movement stop AFK test";
    }

    @Override
    public int ticksToWait() {
        return 5;
    }
}
