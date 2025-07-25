package net.lapismc.afkplustester;

import net.lapismc.afkplus.AFKPlus;
import net.lapismc.afkplus.api.AFKPlusAPI;
import net.lapismc.afkplus.api.AFKPlusPlayerAPI;
import net.lapismc.afkplus.playerdata.AFKPlusPlayer;
import net.lapismc.afkplustester.tests.AFKTest;
import net.lapismc.afkplustester.tests.start.APIAFKStartTest;
import net.lapismc.afkplustester.tests.start.CommandStartAFK;
import net.lapismc.afkplustester.tests.start.TimedAFKStart;
import net.lapismc.afkplustester.tests.stop.APIAFKStopTest;
import net.lapismc.afkplustester.tests.stop.ChatAFKStop;
import net.lapismc.afkplustester.tests.stop.CommandStopAFK;
import net.lapismc.afkplustester.tests.stop.MovementAFKStop;
import net.lapismc.lapiscore.commands.LapisCoreCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AFKTestCommand extends LapisCoreCommand {

    AFKPlusPlayerAPI playerAPI;
    AFKPlus afkPlus;
    AFKPlusTester plugin;
    List<AFKTest> tests = new ArrayList<>();

    public AFKTestCommand(AFKPlusTester plugin) {
        super(plugin, "AFKTest", "Run a suite of tests on AFKPlus", new ArrayList<>());
        this.plugin = plugin;
        afkPlus = new AFKPlusAPI().getPlugin();
        playerAPI = new AFKPlusPlayerAPI();
        tests.add(new APIAFKStartTest());
        tests.add(new APIAFKStopTest(plugin));
        tests.add(new CommandStartAFK());
        tests.add(new CommandStopAFK());
        tests.add(new TimedAFKStart(afkPlus));
        tests.add(new MovementAFKStop());
        tests.add(new ChatAFKStop());
    }

    @Override
    protected void onCommand(CommandSender commandSender, String[] strings) {
        //Check that it's a player
        if (isNotPlayer(commandSender, "MustBePlayer"))
            return;
        //Get the players class
        AFKPlusPlayer player = playerAPI.getPlayer((OfflinePlayer) commandSender);
        //Check that the player is OP
        if (!Bukkit.getPlayer(player.getUUID()).isOp()) {
            Bukkit.getPlayer(player.getUUID()).sendMessage(plugin.config.getMessage("NotOp"));
            return;
        }
        //Start testing
        AtomicInteger passed = new AtomicInteger();
        AtomicInteger failed = new AtomicInteger();
        plugin.tasks.runTask(() -> {
            for (AFKTest test : tests) {
                plugin.getLogger().info("Starting test " + test.getTestName());
                int ticksToWait = test.ticksToWait();
                plugin.tasks.runTask(() -> test.setup(player), false);
                plugin.tasks.runTaskLater(() -> {
                    boolean testPassed = test.check();
                    if (testPassed)
                        passed.getAndIncrement();
                    else
                        failed.getAndIncrement();
                    plugin.getLogger().info(testPassed ? "Passed" : "Failed");
                }, ticksToWait, false);
                plugin.tasks.runTaskLater(test::revert, ticksToWait + 1, false);
                try {
                    Thread.sleep((long) (((double) ticksToWait / 20 + 0.5) * 1000));
                } catch (InterruptedException ignored) {
                }
                plugin.getLogger().info("Test complete");
            }
            plugin.getLogger().info("All tests completed:");
            plugin.getLogger().info("Passed: " + passed.get());
            plugin.getLogger().info("Failed: " + failed.get());
        }, true);
    }
}
