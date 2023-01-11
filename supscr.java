import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SupremeLookbookScraper {

    public static void main(String[] args) throws IOException {
        // Connect to the website
        Document doc = Jsoup.connect("https://www.supremenewyork.com/lookbook/").get();

        // Get all the items
        Elements items = doc.select("div.lookbook-container a");

        // Create a list to store the items
        List<Item> lookbook = new ArrayList<>();

        // Iterate over the items
        for (Element item : items) {
            String name = item.select("img").attr("alt");
            String imageUrl = item.select("img").attr("src");
            String link = item.attr("href");
            lookbook.add(new Item(name, imageUrl, link));
        }

        // Write the items to a CSV file
        FileWriter csvWriter = new FileWriter("supreme_lookbook.csv");
        csvWriter.append("Name");
        csvWriter.append(",");
        csvWriter.append("Image URL");
        csvWriter.append(",");
        csvWriter.append("Link");
        csvWriter.append("\n");
        
        for (Item item : lookbook) {
            csvWriter.append(item.getName());
            csvWriter.append(",");
            csvWriter.append(item.getImageUrl());
            csvWriter.append(",");
            csvWriter.append(item.getLink());
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
        System.out.println("Scraped " + lookbook.size() + " products");
    }

    private static class Item {
        private final String name;
        private final String imageUrl;
        private final String link;

        public Item(String name, String imageUrl, String link) {
            this.name = name;
            this.imageUrl = imageUrl;
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getLink() {
            return link;
        }
    }
}
