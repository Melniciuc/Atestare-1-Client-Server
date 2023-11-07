//Atestare 1 Melniciuc Victor CR-211

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/calculate", new CalculationHandler());
        server.setExecutor(null);
        server.start();
    }

    static class CalculationHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            String[] parts = path.split("/");
            if (parts.length < 5) {
                sendBadRequest(exchange);
                return;
            }

            String operation = parts[2];
            try {
                double first = Double.parseDouble(parts[3]);
                double second = Double.parseDouble(parts[4]);
                double result = 0;

                switch (operation) {
                    case "add":
                        result = first + second;
                        break;
                    case "substract":
                        result = first - second;
                        break;
                    case "multiply":
                        result = first * second ;
                        break;
                    case "divide":
                        if (second == 0) {
                            sendBadRequest(exchange);
                            return;
                        }
                        result = first / second;
                        break;
                    default:
                        sendBadRequest(exchange);
                        return;
                }

                sendResponse(exchange, result);
            } catch (NumberFormatException e) {
                sendBadRequest(exchange);
            }
        }
    }

    static void sendResponse(HttpExchange exchange, double result) throws IOException {
        String response = Double.toString(result);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    static void sendBadRequest(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(400, 0);
        exchange.getResponseBody().close();
    }
}