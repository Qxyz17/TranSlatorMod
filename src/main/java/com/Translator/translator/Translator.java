package com.Translator.translator;

import com.Translator.config.ModConfig;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {
    // 分割单词和标点（避免翻译时包含符号）
    private static final Pattern PUNCTUATION = Pattern.compile("([.,!?;:'\"()\\[\\]{} ])");

    // 英译中（处理他人消息）
    public static String translateEnToZh(String original) {
        if (original.isEmpty()) return original;

        StringBuilder result = new StringBuilder();
        String[] parts = PUNCTUATION.split(original);
        Matcher matcher = PUNCTUATION.matcher(original);
        int puncIndex = 0;

        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty()) continue;
            // 从词库匹配翻译，无匹配则保留原词
            String translated = DictionaryLoader.EN_TO_ZH.getOrDefault(part.toLowerCase(), part);
            result.append(translated);
            // 拼接标点
            if (matcher.find(puncIndex)) {
                result.append(matcher.group());
                puncIndex = matcher.end();
            }
        }
        return result.toString().trim().replaceAll(" +", " ");
    }

    // 中译英（处理自己消息，暂用）
    public static String translateZhToEn(String original) {
        if (original.isEmpty() || !ModConfig.translateSelf) return original;
        return DictionaryLoader.ZH_TO_EN.getOrDefault(original, original);
    }
}
