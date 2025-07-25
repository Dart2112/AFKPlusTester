package net.lapismc.afkplustester.tests.stop;

import net.lapismc.afkplus.playerdata.AFKPlusPlayer;
import net.lapismc.afkplustester.tests.AFKTest;
import org.bukkit.Bukkit;

public class CommandStopAFK implements AFKTest {

    AFKPlusPlayer player;
    boolean failedSetup = false;

    @Override
    public void setup(AFKPlusPlayer player) {
        this.player = player;
        //Set the player as AFK
        player.forceStartAFK();
        if (!player.isAFK()) {
            failedSetup = true;
            return;
        }
        // Make the player issue the /afk command to leave AFK
        Bukkit.getPlayer(player.getUUID()).chat("/afk");
    }

    @Override
    public boolean check() {
        if(failedSetup)
            return false;
        return !player.isAFK();
    }

    @Override
    public void revert() {
        player.forceStopAFK();
    }

    @Override
    public String getTestName() {
        return "Command AFK Stop";
    }

    @Override
    public int ticksToWait() {
        return 2;
    }
}
