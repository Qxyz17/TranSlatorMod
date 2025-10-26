package com.translator.translator;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DictionaryLoader {
    // 词库映射表
    public static final Map<String, String> EN_TO_ZH = new HashMap<>();  // 英译中
    public static final Map<String, String> ZH_TO_EN = new HashMap<>();  // 中译英

    // 加载所有词库
    public static void loadAllDictionaries() {
        loadDictionary("en_to_zh.txt", EN_TO_ZH);
        loadDictionary("zh_to_en.txt", ZH_TO_EN);
        System.out.println("[Translator] 词库加载完成 - 英译中: " + EN_TO_ZH.size() + "条, 中译英: " + ZH_TO_EN.size() + "条");
    }

    // 加载单个词库（格式：key\tvalue，每行一条）
    private static void loadDictionary(String fileName, Map<String, String> map) {
        try {
            // 读取资源文件
            ResourceLocation location = new ResourceLocation(TranslatorMod.MODID, "lang/" + fileName);
            InputStream input = FMLCommonHandler.instance().getModClassLoader().getResourceAsStream(location.getResourcePath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;  // 跳过注释和空行

                String[] parts = line.split("\t", 2);  // 按Tab分割
                if (parts.length == 2) {
                    String key = parts[0].trim().toLowerCase();  // 英文键忽略大小写
                    String value = parts[1].trim();
                    map.put(key, value);
                }
            }
            reader.close();
            input.close();
        } catch (Exception e) {
            System.err.println("[Translator] 词库加载失败: " + fileName);
            e.printStackTrace();
        }
    }
}