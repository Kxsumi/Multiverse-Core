package org.mvplugins.multiverse.core.world.config;

import java.util.List;

/**
 * A record containing a list of the new WorldConfigs added and a list of the worlds removed from the config.
 *
 * @param newWorlds     List of the new WorldConfigs added
 * @param removedWorlds List of the worlds removed from the config
 */
public record NewAndRemovedWorlds(List<WorldConfig> newWorlds, List<String> removedWorlds) {
}
