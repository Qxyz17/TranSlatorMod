package com.Translator.translator;

import com.Translator.config.ModConfig;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {
    // 匹配标点符号（用于分割单词和标点，避免翻译时包含标点）
    private static final Pattern PUNCTUATION_PATTERN = Pattern.compile("([.,!?;:'\"()\\[\\]{} ])");

    // 英译中（处理他人消息）
    public static String translateEnToZh(String originalText) {
        // 未开启翻译或文本为空，直接返回原文本
        if (originalText.isEmpty() || !ModConfig.translateOthers) {
            return originalText;
        }

        StringBuilder translatedBuilder = new StringBuilder();
        // 分割文本为单词和标点
        String[] segments = PUNCTUATION_PATTERN.split(originalText);
        Matcher matcher = PUNCTUATION_PATTERN.matcher(originalText);
        int punctuationIndex = 0;

        for (String segment : segments) {
            segment = segment.trim();
            if (segment.isEmpty()) continue;

            // 优先从词库匹配翻译，无匹配则保留原词
            String translatedSegment = DictionaryLoader.EN_TO_ZH.getOrDefault(segment.toLowerCase(), segment);
            translatedBuilder.append(translatedSegment);

            // 拼接分割出的标点符号
            if (matcher.find(punctuationIndex)) {
                translatedBuilder.append(matcher.group());
                punctuationIndex = matcher.end();
            }
        }

        // 去除多余空格，返回最终翻译结果
        return translatedBuilder.toString().trim().replaceAll(" +", " ");
    }

    // 中译英（处理自己消息，暂未启用）
    public static String translateZhToEn(String originalText) {
        if (originalText.isEmpty() || !ModConfig.translateSelf) {
            return originalText;
        }
        // 直接从词库匹配，无匹配则保留原文本
        return DictionaryLoader.ZH_TO_EN.getOrDefault(originalText, originalText);
    }
}
