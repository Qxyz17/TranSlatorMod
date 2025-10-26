package com.translator;

import com.translator.config.ModConfig;
import com.translator.event.ChatEvent;
import com.translator.event.CommandEvent;
import com.translator.translator.DictionaryLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = TranslatorMod.MODID,
    name = TranslatorMod.NAME,
    version = TranslatorMod.VERSION
)
public class TranslatorMod {
    public static final String MODID = "translatormod";
    public static final String NAME = "Chat Translator";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(MODID)
    public static TranslatorMod instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // 初始化配置
        ModConfig.init(event.getSuggestedConfigurationFile());
        // 加载词库
        DictionaryLoader.loadAllDictionaries();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // 注册事件监听器
        MinecraftForge.EVENT_BUS.register(new ChatEvent());
        MinecraftForge.EVENT_BUS.register(new CommandEvent());
    }
}