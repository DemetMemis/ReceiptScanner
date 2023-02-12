import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Main {
    static class Product {
        String name;
        boolean domestic;
        double price;
        String description;
        int weight;

        public Product(String name, boolean domestic, double price, String description, int weight) {
            this.name = name;
            this.domestic = domestic;
            this.price = price;
            this.description = description;
            this.weight = weight;
        }

        public String getName() {
            return name;
        }

        public boolean isDomestic() {
            return domestic;
        }

        public double getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }

        public int getWeight() {
            return weight;
        }
    }

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://interview-task-api.mca.dev/qr-scanner-codes/alpha-qr-gFpwhsQ8fkY1");
        InputStreamReader reader = new InputStreamReader(url.openStream());

        JsonParser parser = new JsonParser();
        JsonArray productsArray = parser.parse(reader).getAsJsonArray();

        List<Product> products = new ArrayList<>();
        for (JsonElement productElement : productsArray) {
            Product product = new Gson().fromJson(productElement, Product.class);
            products.add(product);
        }

        double domesticCost = 0;
        double importedCost = 0;
        int domesticCount = 0;
        int importedCount = 0;


        List<Product> domesticProducts = new ArrayList<>();
        List<Product> importedProducts = new ArrayList<>();

        for (Product product : products) {
            if (product.domestic) {
                domesticProducts.add(product);
            } else {
                importedProducts.add(product);
            }
        }


        Collections.sort(domesticProducts, (o1, o2) -> o1.name.compareTo(o2.name));


        Collections.sort(importedProducts, (o1, o2) -> o1.name.compareTo(o2.name));

        System.out.println("Domestic");

        for (Product product : domesticProducts) {
            System.out.println(String.format("%s ... Price: $%.1f %s  Weight: %s",
                    product.name,
                    product.price,
                    product.description.length() > 10 ? product.description.substring(0, 10) + "..." : product.description,
                    product.weight == 0 ? "N/A ..." : product.weight + "g"));
            domesticCost += product.price;
            domesticCount++;
        }

        System.out.println("Imported");

        for (Product product : importedProducts) {
            System.out.println(String.format("%s ... Price: $%.1f %s  Weight: %s",
                    product.name,
                    product.price,
                    product.description.length() > 10 ? product.description.substring(0, 10) + "..." : product.description,
                    product.weight == 0 ? "N/A ..." : product.weight + "g"));
            importedCost += product.price;
            importedCount++;


            System.out.println("Domestic cost: $" + domesticCost + " Imported cost: $" + importedCost +
                    " Domestic count: " + domesticCount + " Imported count: " + importedCount);
        }
    }
}

