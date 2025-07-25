package net.lapismc.afkplustester.tests;

import net.lapismc.afkplus.playerdata.AFKPlusPlayer;

/**
 * An interface used to make tests for AFKPlus functionality
 * Each test is assumed to start with the player in a non AFK state and default config
 * Each test should revert to this state also, reversing any changes they make
 * All methods are called on the main thread as outlines below
 * Setup is called on the first tick of the test
 * Check is called ticksToWait() ticks later
 * Revert is called on the next tick after check
 */
public interface AFKTest {

    /**
     * Create the conditions you expect to test
     * For example changing config values, setting AFK states, interacting with the player
     */
    void setup(AFKPlusPlayer player);

    /**
     * Check if the test passed
     * For example did the player become AFK
     *
     * @return true if the test passed, otherwise false
     */
    boolean check();

    /**
     * Revert all changes to the default state
     * For example delete the config so a new one can be generated, set the player back as not AFK etc.
     */
    void revert();

    /**
     * Get the name of the test in a human readable form
     *
     * @return the name of the test
     */
    String getTestName();

    /**
     * How many ticks should we wait between running Setup and running check
     * @return an int of how many ticks to wait
     */
    int ticksToWait();

}
