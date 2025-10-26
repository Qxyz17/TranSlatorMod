package com.Translator.translator;

import com.Translator.TranslatorMod;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraft.util.ResourceLocation;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DictionaryLoader {
    // 英译中、中译英词库
    public static final Map<String, String> EN_TO_ZH = new HashMap<>();
    public static final Map<String, String> ZH_TO_EN = new HashMap<>();

    // 加载所有词库
    public static void loadAllDictionaries() {
        loadDictionary("en_to_zh.txt", EN_TO_ZH);
        loadDictionary("zh_to_en.txt", ZH_TO_EN);
        // 打印加载结果（调试用）
        System.out.println("[TranslatorMod] 词库加载完成 - 英译中: " + EN_TO_ZH.size() + "条 | 中译英: " + ZH_TO_EN.size() + "条");
    }

    // 加载单个词库文件
    private static void loadDictionary(String fileName, Map<String, String> dictMap) {
        try {
            // 1.8.9 正确的资源加载方式（替换 getModClassLoader 为 getClassLoader）
            ResourceLocation location = new ResourceLocation(TranslatorMod.MODID, "lang/" + fileName);
            InputStream input = FMLCommonHandler.instance().getClassLoader().getResourceAsStream(location.getResourcePath());

            if (input == null) {
                System.err.println("[TranslatorMod] 词库文件缺失: " + location.getResourcePath());
                return;
            }

            // 按行读取词库（UTF-8 编码避免中文乱码）
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // 跳过空行和注释（# 开头）
                if (line.isEmpty() || line.startsWith("#")) continue;

                // 按 Tab 分割关键词和翻译结果（支持翻译结果含空格）
                String[] parts = line.split("\t", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    dictMap.put(key, value);
                }
            }

            reader.close();
            input.close();
        } catch (Exception e) {
            System.err.println("[TranslatorMod] 词库加载失败: " + fileName);
            e.printStackTrace();
        }
    }
}
