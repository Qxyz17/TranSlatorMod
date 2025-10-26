package com.Translator.translator;

import com.Translator.TranslatorMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DictionaryLoader {
    public static final Map<String, String> EN_TO_ZH = new HashMap<>();
    public static final Map<String, String> ZH_TO_EN = new HashMap<>();

    // 加载词库（从 assets/translatormod/lang/ 读取）
    public static void loadAllDictionaries() {
        loadDict("en_to_zh.txt", EN_TO_ZH);
        loadDict("zh_to_en.txt", ZH_TO_EN);
        System.out.println("[TranslatorMod] 词库加载：英译中" + EN_TO_ZH.size() + "条，中译英" + ZH_TO_EN.size() + "条");
    }

    private static void loadDict(String fileName, Map<String, String> dict) {
        try {
            ResourceLocation res = new ResourceLocation(TranslatorMod.MODID, "lang/" + fileName);
            InputStream in = FMLCommonHandler.instance().getClassLoader().getResourceAsStream(res.getResourcePath());
            
            if (in == null) {
                System.err.println("[TranslatorMod] 缺失词库：" + fileName);
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\t", 2);
                if (parts.length == 2) {
                    dict.put(parts[0].trim().toLowerCase(), parts[1].trim());
                }
            }
            reader.close();
            in.close();
        } catch (Exception e) {
            System.err.println("[TranslatorMod] 词库加载失败：" + fileName);
        }
    }
}
