package br.com.test.technical.context;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("application")
public class GlobalContext {
    private final Map<String, Object> contextData = new ConcurrentHashMap<>();

    public void put(String key, Object value) {
        contextData.put(key, value);
    }

    public Object get(String key) {
        return contextData.get(key);
    }

    public void remove(String key) {
        contextData.remove(key);
    }
}