package io.github.craftsuperloader.compile.head;

public class Token {
    int line;
    int type;
    String data;

    public Token(int type,String data,int line){
        this.line = line;
        this.type = type;
        this.data = data;
    }

    public int getLine() {
        return line;
    }

    public String getData() {
        return data;
    }

    public int getType() {

        return type;
    }

    public static final int
            INT = 0,
            DBL = 1,
            STR = 2,
            NAM = 3,
            KEY = 4,
            LP = 5,
            LR = 6,
            line_n = 7,
            text_n = 8,
            file_end = -1293901299,
            END = 10,
            SEM = 11;

    @Override
    public String toString() {
        String tpe = "";
        switch (type){
            case INT -> tpe = "INT";
            case STR -> tpe = "STR";
            case DBL -> tpe = "DBL";
            case NAM -> tpe = "NAM";
            case KEY -> tpe = "KEY";
            case LP -> tpe = "LP";
            case LR -> tpe = "LR";
            case SEM -> tpe = "SEM";
            case END -> tpe = "END";
        }
        return tpe + "|" + data;
    }
}
