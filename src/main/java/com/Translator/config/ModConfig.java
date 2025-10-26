package com.translator.config;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class ModConfig {
    // 翻译开关（默认开启）
    public static boolean translateOthers = true;  // 他人消息（英译中）
    public static boolean translateSelf = true;    // 自己消息（中译英）

    private static Configuration config;

    public static void init(File configFile) {
        config = new Configuration(configFile);
        syncConfig();
    }

    private static void syncConfig() {
        config.load();
        // 读取配置
        translateOthers = config.getBoolean(
            "translateOthers", 
            "general", 
            true, 
            "Translate others' English messages to Chinese"
        );
        translateSelf = config.getBoolean(
            "translateSelf", 
            "general", 
            true, 
            "Translate your Chinese messages to English"
        );
        // 保存配置
        if (config.hasChanged()) {
            config.save();
        }
    }

    // 动态更新配置（命令控制时调用）
    public static void updateConfig(boolean isOthers, boolean state) {
        if (isOthers) {
            translateOthers = state;
        } else {
            translateSelf = state;
        }
        syncConfig();
    }
}