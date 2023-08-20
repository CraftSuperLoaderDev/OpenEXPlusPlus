package io.github.craftsuperloader.engine;

import javax.script.*;
import java.io.Reader;
import java.util.concurrent.Callable;

public class OpenEXScriptEngine implements ScriptEngine, Callable {
    @Override
    public Object eval(String script, ScriptContext context) throws ScriptException {
        return null;
    }

    @Override
    public Object eval(Reader reader, ScriptContext context) throws ScriptException {
        return null;
    }

    @Override
    public Object eval(String script) throws ScriptException {
        return null;
    }

    @Override
    public Object eval(Reader reader) throws ScriptException {
        return null;
    }

    @Override
    public Object eval(String script, Bindings n) throws ScriptException {
        return null;
    }

    @Override
    public Object eval(Reader reader, Bindings n) throws ScriptException {
        return null;
    }

    @Override
    public void put(String key, Object value) {

    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public Bindings getBindings(int scope) {
        return null;
    }

    @Override
    public void setBindings(Bindings bindings, int scope) {

    }

    @Override
    public Bindings createBindings() {
        return new SimpleBindings();
    }

    @Override
    public ScriptContext getContext() {
        return null;
    }

    @Override
    public void setContext(ScriptContext context) {

    }

    @Override
    public ScriptEngineFactory getFactory() {
        return null;
    }

    @Override
    public Object call() throws Exception {
        return null;
    }
}
