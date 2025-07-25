package net.lapismc.afkplustester.tests.start;

import net.lapismc.afkplus.AFKPlus;
import net.lapismc.afkplus.playerdata.AFKPlusPlayer;
import net.lapismc.afkplustester.tests.AFKTest;

import java.io.File;

public class TimedAFKStart implements AFKTest {

    AFKPlusPlayer player;
    AFKPlus afkPlus;

    public TimedAFKStart(AFKPlus afkPlus) {
        this.afkPlus = afkPlus;
    }

    @Override
    public void setup(AFKPlusPlayer player) {
        this.player = player;
        //Edit the config to make the player AFK immediately
        afkPlus.getConfig().set("Permissions.afkplus,admin.TimeToAFK", 1);
        afkPlus.saveConfig();
        //Reload the permissions to make sure that the player will have the new value
        afkPlus.perms.loadPermissions();
        //Force run the task to test AFK since its timing might not line up with our plans here
        afkPlus.tasks.runTaskLater(player.getRepeatingTask(), 2 * 20, false);
    }

    @Override
    public boolean check() {
        return player.isAFK();
    }

    @Override
    public void revert() {
        File f = new File(afkPlus.getDataFolder(), "config.yml");
        f.delete();
        player.interact();
        afkPlus.perms.loadPermissions();
    }

    @Override
    public String getTestName() {
        return "Movement AFK start";
    }

    @Override
    public int ticksToWait() {
        return 3 * 20;
    }
}
