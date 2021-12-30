package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeImpl implements Code {
    private static final Pattern PATTERN_FULL = Pattern.compile("(.*?)=(.*?);(.*?)");
    private static final Pattern PATTERN_NO_JUMP = Pattern.compile("(.*?)=(.*?)");
    private static final Pattern PATTERN_NO_DEST = Pattern.compile("(.*?);(.*?)");

    private static final Map<String, String> DEST_MAP = new HashMap<String, String>() {{
        put("M", "001");
        put("D", "010");
        put("MD", "011");
        put("A", "100");
        put("AM", "101");
        put("AD", "110");
        put("AMD", "111");
    }};

    private static final Map<String, String> COMP_MAP = new HashMap<String, String>() {{
        put("0", "0101010");
        put("1", "0111111");
        put("-1", "0111010");
        put("D", "0001100");
        put("A", "0110000");
        put("!D", "0001101");
        put("!A", "0110001");
        put("-D", "0001111");
        put("-A", "0110011");
        put("D+1", "0011111");
        put("A+1", "0110111");
        put("D-1", "0001110");
        put("A-1", "0110010");
        put("D+A", "0000010");
        put("D-A", "0010011");
        put("A-D", "0000111");
        put("D&A", "0000000");
        put("D|A", "0010101");
        put("M", "1110000");
        put("!M", "1110001");
        put("-M", "1110011");
        put("M+1", "1110111");
        put("M-1", "1110010");
        put("D+M", "1000010");
        put("D-M", "1010011");
        put("M-D", "1000111");
        put("D&M", "1000000");
        put("D|M", "1010101");
    }};


    private static final Map<String, String> JUMP_MAP = new HashMap<String, String>() {{
        put("JGT", "001");
        put("JEQ", "010");
        put("JGE", "011");
        put("JLT", "100");
        put("JNE", "101");
        put("JLE", "110");
        put("JMP", "111");
    }};

    @Override
    public String dest(String source) {
        Matcher matcher = PATTERN_NO_JUMP.matcher(source);
        if (!matcher.matches()) {
            return "000";
        }
        String dest = matcher.group(1);
        return DEST_MAP.getOrDefault(dest, "000");
    }

    @Override
    public String comp(String source) {
        Matcher matcherFull = PATTERN_FULL.matcher(source);
        if (matcherFull.matches()) {
            return COMP_MAP.getOrDefault(matcherFull.group(2), null);
        }
        Matcher matcherJump = PATTERN_NO_JUMP.matcher(source);
        if (matcherJump.matches()) {
            return COMP_MAP.getOrDefault(matcherJump.group(2), null);
        }
        Matcher matcherDest = PATTERN_NO_DEST.matcher(source);
        if (matcherDest.matches()) {
            return COMP_MAP.get(matcherDest.group(1));
        }
        return null;
    }

    @Override
    public String jump(String source) {
        Matcher matcherFull = PATTERN_FULL.matcher(source);
        if (matcherFull.matches()) {
            return JUMP_MAP.getOrDefault(matcherFull.group(3), null);
        }
        Matcher matcherJump = PATTERN_NO_JUMP.matcher(source);
        if (matcherJump.matches()) {
            return "000";
        }
        Matcher matcherDest = PATTERN_NO_DEST.matcher(source);
        if (matcherDest.matches()) {
            return JUMP_MAP.get(matcherDest.group(2));
        }
        return "000";
    }
}
