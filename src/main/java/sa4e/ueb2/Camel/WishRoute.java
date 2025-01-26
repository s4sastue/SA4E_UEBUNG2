package sa4e.ueb2.Camel;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import java.nio.file.Files;
import java.nio.file.Paths;


public class WishRoute extends RouteBuilder {

    @Override
    public void configure(){

        from("file:/home/stuelbsasc/wishes?noop=true")
                .routeId("analogWishesRoute")

                .log("Neuen Wunsch im Postfach eingetroffen gefunden: ${header.CamelFileName}")

                .process(exchange -> {
                    String filePath = exchange.getIn().getHeader(Exchange.FILE_PATH, String.class);
                    String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

                    exchange.getIn().setBody(fileContent);
                })

                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:4242/api")

                .log("Wunsch an das XmasWishes-System übertragen --> Antowrt vom System ${body}");
    }
}