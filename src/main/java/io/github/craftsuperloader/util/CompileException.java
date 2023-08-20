package io.github.craftsuperloader.util;

import io.github.craftsuperloader.MetaData;
import io.github.craftsuperloader.compile.head.Token;

import java.io.PrintStream;

public class CompileException extends RuntimeException{

    Token token;
    String filename;
    Throwable throwable;
    String message;
    int status = 0;

    public CompileException(Exception e){
        token = null;
        filename = null;
        throwable = e;
        this.status = 0;
    }

    public CompileException(String message,String filename){
        this.message = message;
        this.filename = filename;
        this.status = 2;
    }

    public CompileException(String message,Token token,String filename){
        this.token = token;
        this.filename = filename;
        this.status = 1;
        this.message = message;
    }

    @Override
    public void printStackTrace(PrintStream s) {
        if(status == 0) {
            s.println("Compile Error: "+throwable.getClass().getSimpleName() +
                    "\n\tMessage: "+throwable.getLocalizedMessage()+
                    "\n\tCompile: "+ MetaData.COMPILE_VERSION+
                    "\n\tRuntime: "+ MetaData.RUNTIME_VERSION);
            throwable.printStackTrace(s);
        }else if(status == 1){
            s.println("Compile Error: "+message+
                    "\n\tToken: "+token.getData()+
                    "\n\tLine: "+token.getLine()+
                    "\n\tFilename: "+filename+
                    "\n\tCompile: "+ MetaData.COMPILE_VERSION+
                    "\n\tRuntime: "+ MetaData.RUNTIME_VERSION);
        }else if(status == 2){
            s.println("Compile Error: "+message+
                    "\n\tCompile: "+ MetaData.COMPILE_VERSION+
                    "\n\tRuntime: "+ MetaData.RUNTIME_VERSION);
        }
    }
}
