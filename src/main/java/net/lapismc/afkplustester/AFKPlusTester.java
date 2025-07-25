package net.lapismc.afkplustester;

import net.lapismc.lapiscore.LapisCorePlugin;

public class AFKPlusTester extends LapisCorePlugin {

    @Override
    public void onEnable() {
        new AFKTestCommand(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
