//Atestare 1 Melniciuc Victor CR-211

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceți operația (add, substract, multiply, divide): ");
        String operation = scanner.next();

        System.out.print("Primul număr: ");
        double first = scanner.nextDouble();

        System.out.print("Al doilea număr: ");
        double second = scanner.nextDouble();

        String baseUrl = "http://localhost:8080/calculate";
        String url = baseUrl + "/" + operation + "/" + first + "/" + second;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                InputStream responseStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(responseStream);
                int data = reader.read();
                StringBuilder response = new StringBuilder();
                while (data != -1) {
                    response.append((char) data);
                    data = reader.read();
                }
                reader.close();
                System.out.println("Succes: " + response);
            } else {
                System.out.println("Fail: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}