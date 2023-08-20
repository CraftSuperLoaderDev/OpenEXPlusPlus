package io.github.craftsuperloader.compile.head;

import io.github.craftsuperloader.MetaData;
import io.github.craftsuperloader.compile.Compile;
import io.github.craftsuperloader.util.CompileException;

import java.util.ArrayList;

public final class LexicalAnalysis {
    Compile compile;
    int index;
    int file_line;
    char[] data;
    Character buf;
    boolean isl = false;

    public LexicalAnalysis(String data,Compile compile){
        this.compile = compile;
        this.data = data.toCharArray();
        this.file_line = 1;
    }

    private int getChar(){
        if(index >= data.length) return Token.file_end;
        char c;
        if(buf == null){
            c = data[index];
            index += 1;
        }else {
            c = buf;
            buf = null;
        }
        return c;
    }

    public ArrayList<Token> getLexer(){
        ArrayList<Token> tokens = new ArrayList<>();
        Token t;
        try {
            while ((t = lex()) != null){
                if(t.type==Token.line_n||t.type==Token.text_n)continue;
                tokens.add(t);
            }
        } catch (NullPointerException e) {
            return tokens;
        }
        return tokens;
    }

    private Token lex() throws NullPointerException {
        StringBuilder sb = new StringBuilder();
        int c;
        do {
            c = getChar();
        } while (isSpace(c));
        if (isSEM(c)) {
            sb.append((char) c);
            return new Token(Token.SEM, sb.toString(), file_line);
        } else if (isNum(c)) {
            boolean isdouble = false;
            do {
                sb.append((char) c);
                c = getChar();
                if (c == '.') isdouble = true;
            } while (isNum(c) || c == '.');
            if (isKey(c)) {
                do {
                    sb.append((char) c);
                    c = getChar();
                } while (isKey(c) || isNum(c));
                buf = (char) c;
                return new Token(Token.NAM, sb.toString(), file_line);
            }else if(c=='-'){
                isl = true;
            }
            buf = (char) c;
            if (isdouble) return new Token(Token.DBL, sb.toString(), file_line);
            else return new Token(Token.INT, sb.toString(), file_line);
        } else if (isKey(c)) {
            do {
                sb.append((char) c);
                c = getChar();
                if (isSEM(c)) break;
            } while (isKey(c) || isNum(c));
            buf = (char) c;
            if (MetaData.isKey(sb.toString())) return new Token(Token.KEY, sb.toString(), file_line);
            return new Token(Token.NAM, sb.toString(), file_line);
        } else if (c == '/') {
            sb.append((char) c);
            c = getChar();
            if (c == '*') {
                do {
                    do {
                        c = getChar();
                        sb.append((char) c);
                    } while (c != '*');
                    c = getChar();
                    sb.append((char) c);
                } while (c != '/');
                sb.deleteCharAt(0).deleteCharAt(sb.length() - 1).deleteCharAt(sb.length() - 1);
                return new Token(Token.text_n, sb.toString(), file_line);
            } else return new Token(Token.SEM, "/", file_line);
        } else if (c == '*') return new Token(Token.SEM, "*", file_line);
        else if (c == '"') {
            do {
                c = getChar();
                if (c == '\n')
                    throw new CompileException("'\"' expected.", new Token(Token.STR, sb.toString(), file_line), compile.getFilename());
                if (c == '\\') {
                    c = getChar();
                    if (c == 'n') {
                        sb.append("\n");
                    } else if (c == 't') {
                        sb.append("\t");
                    } else if (c == '\\') {
                        sb.append("\\");
                    } else if (c == '"') {
                        sb.append("\"");
                    } else
                        throw new CompileException("Illegal escape character in string literal.", new Token(Token.STR, sb.toString(), file_line), compile.getFilename());
                    continue;
                }
                sb.append((char) c);
            } while (c != '"');
            sb.deleteCharAt(sb.indexOf("\""));
            return new Token(Token.STR, sb.toString(), file_line);
        } else if (c == '=') {
            sb.append((char) c);
            c = getChar();
            if (c == '=' || c == '!') {
                sb.append((char) c);
                return new Token(Token.SEM, sb.toString(), file_line);
            }
            buf = (char) c;
            return new Token(Token.SEM, sb.toString(), file_line);
        } else if (c == '>' || c == '<') {
            sb.append((char) c);
            c = getChar();
            if (c == '=') {
                sb.append((char) c);
                return new Token(Token.SEM, sb.toString(), file_line);
            }
            buf = (char) c;
            return new Token(Token.SEM, sb.toString(), file_line);
        } else if (c == '+') {
            sb.append((char) c);
            c = getChar();
            if (c == '=') {
                sb.append((char) c);
                return new Token(Token.SEM, sb.toString(), file_line);
            }
            buf = (char) c;
            return new Token(Token.SEM, sb.toString(), file_line);
        } else if (c == '-') {
            sb.append((char) c);

            if(isl){
                isl = false;
                return new Token(Token.SEM, sb.toString(), file_line);
            }

            c = getChar();
            if (c == '=') {
                sb.append((char) c);
                return new Token(Token.SEM, sb.toString(), file_line);
            }else if (isNum(c)) {
                sb.append((char) c);
                return new Token(Token.INT, sb.toString(), file_line);
            }
            buf = (char) c;
            return new Token(Token.SEM, sb.toString(), file_line);
        } else if (isLP(c)) {
            sb.append((char) c);
            return new Token(Token.LP, sb.toString(), file_line);
        } else if (isLR(c)) {
            sb.append((char) c);
            return new Token(Token.LR, sb.toString(), file_line);
        } else if (c == ';') return new Token(Token.END, "" + ((char) c), file_line);
        else if (c == '\n') {
            file_line += 1;
            return new Token(Token.line_n, "", file_line);
        } else {
            if(c == Token.file_end) return null;
            throw new CompileException("Unknown char in (lines:" + file_line + "): >>" + ((char) c) + "<<", compile.getFilename());
        }
    }

    private boolean isLP(int c) {
        return (c == '(' || c == '[' || c == '{');
    }

    private boolean isLR(int c) {
        return (c == ')' || c == ']' || c == '}');
    }

    private boolean isSpace(int c) {
        return ((char) c == ' ');
    }

    private boolean isKey(int c) {
        char a = (char) c;
        return (a >= 'a' && a <= 'z') || (a >= 'A' && a <= 'Z') || a == '_';
    }

    private boolean isNum(int c) {
        return Character.isDigit((char) c);
    }

    private boolean isSEM(int c) {
        return (c == ':') || (c == '!') || (c == '.') || (c == ',') || (c == '%') || (c == '&') || (c == '|') || (c == '$');
    }
}
