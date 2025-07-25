package net.lapismc.afkplustester.tests.stop;

import net.lapismc.afkplus.playerdata.AFKPlusPlayer;
import net.lapismc.afkplustester.AFKPlusTester;
import net.lapismc.afkplustester.tests.AFKTest;

public class APIAFKStopTest implements AFKTest {

    AFKPlusTester plugin;
    AFKPlusPlayer player;
    boolean failedSetup = false;

    public APIAFKStopTest(AFKPlusTester plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setup(AFKPlusPlayer player) {
        this.player = player;
        //Set the player as AFK so we can test a stop
        player.forceStartAFK();
        if (!player.isAFK())
            failedSetup = true;
        //Stop the players AFK state one tick later
        plugin.tasks.runTask(player::stopAFK, false);
    }

    @Override
    public boolean check() {
        if (failedSetup)
            return false;
        return !player.isAFK();
    }

    @Override
    public void revert() {
        //Just in case it failed
        player.forceStopAFK();
    }

    @Override
    public String getTestName() {
        return "API AFK Stop Test";
    }

    @Override
    public int ticksToWait() {
        return 2;
    }
}
