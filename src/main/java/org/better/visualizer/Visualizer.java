package org.better.visualizer;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;

public final class Visualizer implements ModInitializer {
    public static final String MODID = "visualizer";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing {} for Fabric", MODID);
    }
}
