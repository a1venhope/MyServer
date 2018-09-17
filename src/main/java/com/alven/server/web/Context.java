package com.alven.server.web;

import com.alven.server.handler.Handler;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 此类负责管理 handler 的映射
 */
public class Context {
    // 存储 handler name -- handler class
    private Map<String,String> handlers;
    // 存储 url pattern -- handler name
    private Map<String,String> mapping;

    public Context() {
        handlers = new HashMap<>();
        mapping = new HashMap<>();
    }

    public Map<String, String> getHandlers() {
        return handlers;
    }

    public void setHandlers(Map<String, String> handlers) {
        this.handlers = handlers;
    }

    public Map<String, String> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }


    public Handler getHandler(String urlPattern) {
        String handlerName = mapping.get(urlPattern);
        if(null != handlerName) {
            String handlerClass = handlers.get(handlerName);
            if(null != handlerClass || !handlerClass.trim().equals("")) {
                try {
                    return (Handler) Class.forName(handlerClass).getConstructor().newInstance();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
