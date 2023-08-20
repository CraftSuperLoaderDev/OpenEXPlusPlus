package io.github.craftsuperloader.compile;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Compiler {
    private static final Logger log = Logger.getLogger(Compiler.class.getName());
    static ExecutorService executor = Executors.newCachedThreadPool(r -> {
        Thread thread = new Thread(r);
        thread.setName("Compiler-Thread");
        return thread;
    });
    static ArrayList<Compile> compiles = new ArrayList<>();

    public static Logger getLogger() {
        return log;
    }

    public static void addTask(String filename){
        executor.execute(() -> {
            Compile compile = new Compile(new File(filename));
            compile.compile();
            compiles.add(compile);
        });
    }

    public static void shutdownCompilerGroup(){
        executor.shutdown();
    }

}
