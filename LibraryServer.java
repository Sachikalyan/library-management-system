import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * LibraryServer is a lightweight, pure Java HTTP Web Server.
 * Uses Java's built-in HttpServer (NO external libraries needed!).
 * Perfect for 1st-year demonstrations and cloud hosting.
 */
public class LibraryServer {
    private static Library library = new Library();

    public static void main(String[] args) throws IOException {
        // Read port from environment variable (used by cloud hosts like Render/Railway/Heroku)
        // or default to port 8080 locally.
        int port = 8080;
        String envPort = System.getenv("PORT");
        if (envPort != null && !envPort.isEmpty()) {
            try {
                port = Integer.parseInt(envPort);
            } catch (NumberFormatException ignored) {}
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("=================================================");
        System.out.println("  🚀 Library Management Server is starting...   ");
        System.out.println("  🌐 Running at: http://localhost:" + port);
        System.out.println("=================================================");

        // Static Web Page Handler (serves index.html)
        server.createContext("/", new StaticFileHandler());

        // REST API Handlers
        server.createContext("/api/books", new BooksApiHandler());
        server.createContext("/api/issue", new IssueApiHandler());
        server.createContext("/api/return", new ReturnApiHandler());

        server.setExecutor(null); // default multithreaded executor
        server.start();
        System.out.println("✅ Server is running! Press Ctrl+C to stop.");
    }

    // Handler to serve the frontend index.html
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            File file = new File("index.html");
            if (!file.exists()) {
                String notFound = "<h1>404 - index.html not found</h1>";
                sendResponse(exchange, 404, "text/html", notFound.getBytes(StandardCharsets.UTF_8));
                return;
            }

            byte[] bytes = new byte[(int) file.length()];
            try (FileInputStream fis = new FileInputStream(file)) {
                fis.read(bytes);
            }
            sendResponse(exchange, 200, "text/html; charset=UTF-8", bytes);
        }
    }

    // Handler for GET /api/books and POST /api/books
    static class BooksApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            String method = exchange.getRequestMethod();

            if ("OPTIONS".equalsIgnoreCase(method)) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if ("GET".equalsIgnoreCase(method)) {
                StringBuilder json = new StringBuilder("[");
                var books = library.getBooks();
                for (int i = 0; i < books.size(); i++) {
                    Book b = books.get(i);
                    json.append(String.format(
                        "{\"id\":%d,\"title\":\"%s\",\"author\":\"%s\",\"isIssued\":%b}",
                        b.getId(), escapeJson(b.getTitle()), escapeJson(b.getAuthor()), b.isIssued()
                    ));
                    if (i < books.size() - 1) json.append(",");
                }
                json.append("]");
                sendResponse(exchange, 200, "application/json", json.toString().getBytes(StandardCharsets.UTF_8));
            } else if ("POST".equalsIgnoreCase(method)) {
                // Parse query parameters from URL: /api/books?id=105&title=xyz&author=abc
                Map<String, String> params = parseQueryParams(exchange.getRequestURI());
                try {
                    int id = Integer.parseInt(params.get("id"));
                    String title = params.get("title");
                    String author = params.get("author");

                    if (title == null || author == null || title.isBlank() || author.isBlank()) {
                        sendResponse(exchange, 400, "application/json", "{\"error\":\"Title and Author required\"}".getBytes(StandardCharsets.UTF_8));
                        return;
                    }

                    boolean added = library.addBook(new Book(id, title, author));
                    if (added) {
                        sendResponse(exchange, 201, "application/json", "{\"message\":\"Book added successfully\"}".getBytes(StandardCharsets.UTF_8));
                    } else {
                        sendResponse(exchange, 409, "application/json", "{\"error\":\"Book ID already exists\"}".getBytes(StandardCharsets.UTF_8));
                    }
                } catch (Exception e) {
                    sendResponse(exchange, 400, "application/json", "{\"error\":\"Invalid ID or parameters\"}".getBytes(StandardCharsets.UTF_8));
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    // Handler to issue a book: POST /api/issue?id=101
    static class IssueApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            Map<String, String> params = parseQueryParams(exchange.getRequestURI());
            try {
                int id = Integer.parseInt(params.get("id"));
                boolean success = library.issueBook(id);
                if (success) {
                    sendResponse(exchange, 200, "application/json", "{\"message\":\"Book issued successfully\"}".getBytes(StandardCharsets.UTF_8));
                } else {
                    sendResponse(exchange, 400, "application/json", "{\"error\":\"Cannot issue book (either not found or already issued)\"}".getBytes(StandardCharsets.UTF_8));
                }
            } catch (Exception e) {
                sendResponse(exchange, 400, "application/json", "{\"error\":\"Invalid ID\"}".getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    // Handler to return a book: POST /api/return?id=101
    static class ReturnApiHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            addCorsHeaders(exchange);
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            Map<String, String> params = parseQueryParams(exchange.getRequestURI());
            try {
                int id = Integer.parseInt(params.get("id"));
                boolean success = library.returnBook(id);
                if (success) {
                    sendResponse(exchange, 200, "application/json", "{\"message\":\"Book returned successfully\"}".getBytes(StandardCharsets.UTF_8));
                } else {
                    sendResponse(exchange, 400, "application/json", "{\"error\":\"Cannot return book (either not found or not issued)\"}".getBytes(StandardCharsets.UTF_8));
                }
            } catch (Exception e) {
                sendResponse(exchange, 400, "application/json", "{\"error\":\"Invalid ID\"}".getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String contentType, byte[] data) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(statusCode, data.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(data);
        }
    }

    private static void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    }

    private static Map<String, String> parseQueryParams(URI uri) {
        Map<String, String> map = new HashMap<>();
        String query = uri.getQuery();
        if (query == null) return map;
        for (String pair : query.split("&")) {
            String[] parts = pair.split("=");
            if (parts.length == 2) {
                map.put(parts[0], java.net.URLDecoder.decode(parts[1], StandardCharsets.UTF_8));
            }
        }
        return map;
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }
}
