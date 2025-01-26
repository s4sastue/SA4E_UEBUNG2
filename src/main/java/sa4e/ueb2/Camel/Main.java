package sa4e.ueb2.Camel;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new WishRoute());

        context.start();
        while (true) {
            Thread.sleep(Long.MAX_VALUE);
        }
    }
}
