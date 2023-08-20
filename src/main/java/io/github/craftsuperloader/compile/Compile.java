package io.github.craftsuperloader.compile;

import io.github.craftsuperloader.compile.head.LexicalAnalysis;
import io.github.craftsuperloader.compile.head.Token;
import io.github.craftsuperloader.util.CompileException;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class Compile {
    File file;

    public Compile(File file){
        this.file = file;
    }

    public static String readFile(File file){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine())!=null) sb.append(line.split("//")[0]).append("\n");
        }catch (IOException io){
            throw new CompileException(io);
        }
        return sb.toString();
    }

    public String getFilename() {
        return file.getName();
    }

    public void compile(){
        ArrayList<Token> tokens = new LexicalAnalysis(readFile(file),this).getLexer();

        System.out.println(tokens);
    }
}
