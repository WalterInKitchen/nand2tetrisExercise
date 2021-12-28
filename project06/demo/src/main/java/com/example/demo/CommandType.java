package com.example.demo;

public enum CommandType {
    ADDRESS_CMD() {
        @Override
        public boolean match(String cmd) {
            return cmd != null && cmd.startsWith("@") && cmd.length() > 1;
        }

        @Override
        public String parseSymbol(String cmd) {
            return cmd.substring(1);
        }
    },
    CONTROL_CMD() {
        @Override
        public boolean match(String cmd) {
            return false;
        }

        @Override
        public String parseDest(String cmd) {
            return "";
        }
    },
    LOCATION_CMD() {
        @Override
        public boolean match(String cmd) {
            return false;
        }

        @Override
        public String parseSymbol(String cmd) {
            return "";
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
        return null;
    }

    public String parseJump(String cmd) {
        return null;
    }
}
