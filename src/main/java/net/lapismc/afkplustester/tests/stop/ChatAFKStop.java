package net.lapismc.afkplustester.tests.stop;

import net.lapismc.afkplus.playerdata.AFKPlusPlayer;
import net.lapismc.afkplustester.tests.AFKTest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatAFKStop implements AFKTest {

    boolean failedSetup = false;
    AFKPlusPlayer player;

    @Override
    public void setup(AFKPlusPlayer player) {
        this.player = player;
        player.forceStartAFK();
        if (!player.isAFK()) {
            failedSetup = true;
            return;
        }
        Player p = Bukkit.getPlayer(player.getUUID());
        p.chat("Message to leave AFK");
    }

    @Override
    public boolean check() {
        return !player.isAFK();
    }

    @Override
    public void revert() {
        player.interact();
    }

    @Override
    public String getTestName() {
        return "Chat Stop AFK";
    }

    @Override
    public int ticksToWait() {
        return 5;
    }
}
