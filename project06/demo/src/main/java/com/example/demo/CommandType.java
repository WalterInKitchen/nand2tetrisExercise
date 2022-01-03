package com.example.demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CommandType {
    ADDRESS_CMD() {
        @Override
        public boolean match(String cmd) {
            return cmd != null && cmd.startsWith("@") && cmd.length() > 1;
        }

        @Override
        public String parseSymbol(String cmd) {
            return cmd.trim().substring(1);
        }
    }, CONTROL_CMD() {
        private final Pattern patternWithoutJump = Pattern.compile("(.*?)=(.*?)(//.*)?");
        private final Pattern patternWithoutDest = Pattern.compile("(.*?);(.*?)(//.*)?");
        private final Pattern pattern = Pattern.compile("(.*?)=(.*?);(.*?)(//.*)?");
        private final Pattern patternFocusDest = Pattern.compile(".*?=.*?(//.*)?");
        private final Set<String> destSet = new HashSet<>(Arrays.asList("M", "D", "MD", "A", "AM", "AD", "AMD"));
        private final Set<String> jumpSet = new HashSet<>(Arrays.asList("JGT", "JEQ", "JGE", "JLT", "JNE", "JLE", "JMP"));

        @Override
        public boolean match(String cmd) {
            if (pattern.matcher(cmd).matches()) {
                return true;
            }
            if (patternWithoutDest.matcher(cmd).matches()) {
                return true;
            }
            return patternWithoutJump.matcher(cmd).matches();
        }

        @Override
        public String parseDest(String cmd) {
            if (!patternFocusDest.matcher(cmd).matches()) {
                return "null";
            }
            String dest = cmd.substring(0, cmd.indexOf("=")).trim();
            if (destSet.contains(dest)) {
                return dest.trim().toUpperCase(Locale.US);
            }
            return "null";
        }

        @Override
        public String parseComp(String cmd) {
            Matcher matcher = pattern.matcher(cmd);
            if (matcher.matches()) {
                return matcher.group(2).trim();
            }
            Matcher matcherWithoutDest = patternWithoutDest.matcher(cmd);
            if (matcherWithoutDest.matches()) {
                return matcherWithoutDest.group(1).trim();
            }
            Matcher matcherWithoutJump = patternWithoutJump.matcher(cmd);
            if (matcherWithoutJump.matches()) {
                return matcherWithoutJump.group(2).trim();
            }
            throw new RuntimeException("there's no comp part in cmd:" + cmd);
        }

        @Override
        public String parseJump(String cmd) {
            Matcher matcher = pattern.matcher(cmd);
            if (matcher.matches()) {
                return convertJumToSymbol(matcher.group(3)).trim();
            }
            Matcher matcherWithoutDest = patternWithoutDest.matcher(cmd);
            if (matcherWithoutDest.matches()) {
                return convertJumToSymbol(matcherWithoutDest.group(2)).trim();
            }
            return "null";
        }

        private String convertJumToSymbol(String symbol) {
            String upSym = symbol.toUpperCase(Locale.US).trim();
            if (jumpSet.contains(upSym)) {
                return upSym;
            }
            return "null";
        }
    }, LOCATION_CMD() {
        private final Pattern pattern = Pattern.compile("\\(.*\\)");

        @Override
        public boolean match(String cmd) {
            return pattern.matcher(cmd).matches();
        }

        @Override
        public String parseSymbol(String cmd) {
            return cmd.trim().substring(1, cmd.length() - 1);
        }
    };

    public abstract boolean match(String cmd);

    public String parseSymbol(String cmd) {
        return null;
    }

    public String parseDest(String cmd) {
        return null;
    }

    public String parseComp(String cmd) {
        throw new RuntimeException("this kind of cmd can not parse comp");
    }

    public String parseJump(String cmd) {
        throw new RuntimeException("this kind of cmd can not parse jump");
    }
}
