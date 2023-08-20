package io.github.craftsuperloader;

import java.util.HashSet;

public class MetaData {
    public static final String EX_NAME = "OpenEXPlusPlus";
    public static final String COMPILE_VERSION = EX_NAME+"/Compiler/v0.0.1";
    public static final String RUNTIME_VERSION = EX_NAME+"/Runtime/v0.0.1";
    static HashSet<String> keys = new HashSet<>();
    static {

    }

    public static boolean isKey(String s){
        return keys.contains(s);
    }
}
