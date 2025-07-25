package net.lapismc.afkplustester.tests.start;

import net.lapismc.afkplus.playerdata.AFKPlusPlayer;
import net.lapismc.afkplustester.tests.AFKTest;
import org.bukkit.Bukkit;

public class CommandStartAFK implements AFKTest {

    AFKPlusPlayer player;

    @Override
    public void setup(AFKPlusPlayer player) {
        this.player = player;
        //Get the player to issue the AFK command
        Bukkit.getPlayer(player.getUUID()).chat("/afk");
    }

    @Override
    public boolean check() {
        return player.isAFK();
    }

    @Override
    public void revert() {
        player.forceStopAFK();
    }

    @Override
    public String getTestName() {
        return "Command start AFK";
    }

    @Override
    public int ticksToWait() {
        return 2;
    }
}
