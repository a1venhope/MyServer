import com.alven.server.web.Context;
import com.alven.server.entity.Request;
import com.alven.server.web.WebXMLParser;
import org.junit.Test;

public class FunctionTest {

    @Test
    public void testParseRequestParams() {
        Request request = new Request();
        request.parseRequestParams("name=jack&p=1&p=2&p=3&city=LA");
        request.display();
    }

    @Test
    public void testParseRequestInfo() {
        Request request = new Request();
        request.setRequestInfo("GET /index.html?name=Peter&city=Boston HTTP/1.1\r\n" +
                "xxx");
        request.parseRequestInfo();
        request.display();

        Request request1 = new Request();
        request1.setRequestInfo("POST /login HTTP/1.1\r\nxxx\r\n\r\nname=Peter&city=Boston");
        request1.parseRequestInfo();
        request1.display();
    }

    @Test
    public void testXMLParse() {
        Context context = new Context();
        WebXMLParser parser = new WebXMLParser();
        parser.initHandler(context);
        for(String key : context.getHandlers().keySet()) {
            System.out.println(key + " : " + context.getHandlers().get(key));
        }
        for(String key : context.getMapping().keySet()) {
            System.out.println(key + " --> " + context.getMapping().get(key));
        }
    }

}
