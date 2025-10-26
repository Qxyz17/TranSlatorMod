package com.Translator;

import com.Translator.event.ChatEvent;
import com.Translator.event.CommandEvent;
import com.Translator.translator.DictionaryLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

// 模组主类，必须包含 MODID 注解
@Mod(modid = TranslatorMod.MODID, name = TranslatorMod.NAME, version = TranslatorMod.VERSION)
public class TranslatorMod {
    // 模组基础信息（供其他类引用）
    public static final String MODID = "translatormod";
    public static final String NAME = "Chat Translator";
    public static final String VERSION = "1.0.0";

    // 初始化事件
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // 注册事件监听器
        MinecraftForge.EVENT_BUS.register(new ChatEvent());
        MinecraftForge.EVENT_BUS.register(new CommandEvent());
        // 加载词库
        DictionaryLoader.loadAllDictionaries();
        System.out.println("[TranslatorMod] 模组初始化完成！");
    }
}
