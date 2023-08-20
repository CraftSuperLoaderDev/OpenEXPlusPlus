package io.github.craftsuperloader;

import io.github.craftsuperloader.compile.Compiler;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OptionParser parser = new OptionParser(){
            {
                acceptsAll(asList("f","filename"))
                        .withRequiredArg()
                        .withValuesSeparatedBy(',')
                        .ofType(String.class)
                        .describedAs("input file name");
                acceptsAll(asList("v","version"));
            }
        };

        OptionSet set = parser.parse(args);
        if(set.has("version")){
            System.out.println("Compiler: "+MetaData.COMPILE_VERSION+
                    "\nRuntime: "+MetaData.RUNTIME_VERSION);
            return;
        }
        if(set.has("filename")){
            List<String> s = (List<String>) set.valuesOf("filename");
            for(String ss:s)Compiler.addTask(ss);
            Compiler.shutdownCompilerGroup();
        }
    }

    public static List<String> asList(String... arg){
        return Arrays.asList(arg);
    }
}