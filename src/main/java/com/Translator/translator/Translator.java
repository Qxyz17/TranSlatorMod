package com.translator.translator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {
    // 标点符号正则（保留标点位置）
    private static final Pattern PUNCTUATION_PATTERN = Pattern.compile("([.,!?;:'\"()\\[\\]{}])");

    // 英译中（他人消息）
    public static String translateEnToZh(String text) {
        if (text.isEmpty() || !ModConfig.translateOthers) return text;

        StringBuilder result = new StringBuilder();
        String[] segments = PUNCTUATION_PATTERN.split(text);  // 分割文本和标点
        Matcher matcher = PUNCTUATION_PATTERN.matcher(text);
        int puncIndex = 0;

        for (String segment : segments) {
            segment = segment.trim();
            if (segment.isEmpty()) continue;

            // 优先匹配短语，再匹配单词
            String translated = segment;
            for (String key : DictionaryLoader.EN_TO_ZH.keySet()) {
                if (segment.toLowerCase().equals(key)) {
                    translated = DictionaryLoader.EN_TO_ZH.get(key);
                    break;
                }
            }
            result.append(translated);

            // 拼接标点
            if (matcher.find(puncIndex)) {
                result.append(matcher.group());
                puncIndex = matcher.end();
            }
            result.append(" ");
        }

        return result.toString().trim().replaceAll(" +", " ");  // 清理空格
    }

    // 中译英（自己消息）
    public static String translateZhToEn(String text) {
        if (text.isEmpty() || !ModConfig.translateSelf) return text;

        // 直接匹配中文关键词（可根据需求加分词逻辑）
        return DictionaryLoader.ZH_TO_EN.getOrDefault(text, text);
    }
}