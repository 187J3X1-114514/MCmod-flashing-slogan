package io.homo.mcmodsplash.mcmodsplash.fabric.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.homo.mcmodsplash.mcmodsplash.gui.ConfigScreen;

public class ModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreen::buildConfigScreen;
    }
}
