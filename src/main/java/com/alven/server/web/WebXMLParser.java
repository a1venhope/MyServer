package com.alven.server.web;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

/**
 * 该类用于解析 web.xml 文件，初始化 handler
 */
public class WebXMLParser {
    private Context context;

    public void initHandler(Context context) {
        this.context = context;
        parse(new File("src/main/java/com/alven/server/static/web.xml"));
    }

    public void parse(File file) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        List<Element> handlers = root.elements("handler");
        List<Element> handlerMappings = root.elements("handler-mapping");
        parseHandler(handlers);
        parseHandlerMapping(handlerMappings);
    }

    public void parseHandler(List<Element> handlers) {
        for(Element handler : handlers) {
            String handlerName = handler.element("handler-name").getTextTrim();
            String handlerClass = handler.element("handler-class").getTextTrim();
            context.getHandlers().put(handlerName,handlerClass);
        }
    }

    public void parseHandlerMapping(List<Element> handlerMappings) {
        for(Element handlerMapping : handlerMappings) {
            String handlerName = handlerMapping.element("handler-name").getTextTrim();
            List<Element> urlPatterns = handlerMapping.elements("url-pattern");
            for(Element urlPatten : urlPatterns) {
                context.getMapping().put(urlPatten.getTextTrim(),handlerName);
            }
        }
    }

}
