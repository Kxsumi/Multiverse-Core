package com.onarandombox.MultiverseCore.utils.settings.migration;

import java.util.ArrayList;
import java.util.List;

import com.onarandombox.MultiverseCore.utils.settings.MVSettings;

public class MigrateVersion {
    private final double version;
    private final List<MigratorAction> actions;

    protected MigrateVersion(double version, List<MigratorAction> actions) {
        this.version = version;
        this.actions = actions;
    }

    public void migrate(MVSettings settings) {
        actions.forEach(action -> action.migrate(settings));
    }

    public static class Builder {
        private final double version;
        private final List<MigratorAction> actions = new ArrayList<>();

        public Builder(double version) {
            this.version = version;
        }

        public Builder addAction(MigratorAction action) {
            actions.add(action);
            return this;
        }

        public MigrateVersion build() {
            return new MigrateVersion(version, actions);
        }
    }
}
