package net.lapismc.afkplustester.tests.start;

import net.lapismc.afkplus.playerdata.AFKPlusPlayer;
import net.lapismc.afkplustester.tests.AFKTest;

public class APIAFKStartTest implements AFKTest {

    AFKPlusPlayer player;

    @Override
    public void setup(AFKPlusPlayer player) {
        this.player = player;
        player.startAFK();
    }

    @Override
    public boolean check() {
        return player.isAFK();
    }

    @Override
    public void revert() {
        player.stopAFK();
    }

    @Override
    public String getTestName() {
        return "API AFK Start Test";
    }

    @Override
    public int ticksToWait() {
        return 1;
    }

}
