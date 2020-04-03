package com.kevins.application;

import com.kevins.domain.Product;
import com.kevins.domain.converters.StringToByteArrayConverter;
import com.kevins.domain.enums.TaxType;
import com.kevins.infrastructure.DecimalTrie;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.kevins.application.services.MessageService.*;

public class Terminal {

    private static Terminal instance;

    private final DecimalTrie<Product> trie;

    private final StringToByteArrayConverter stringToByteArrayConverter;

    private static final Map<String, Double> TAXES;

    static {
        TAXES = new HashMap<String, Double>() {{
            put("city", 0.02);
            put("state", 0.063);
            put("country", 0.007);
        }};
    }

    private Terminal(DecimalTrie<Product> trie, StringToByteArrayConverter stringToByteArrayConverter) {
        this.trie = trie;
        this.stringToByteArrayConverter = stringToByteArrayConverter;
    }

    public static Terminal getInstance() {
        if (instance == null) {
            instance = new Terminal(
                    new DecimalTrie<>(),
                    new StringToByteArrayConverter()
            );
        }
        return instance;
    }

    public void initTrie(final String initCsvPath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(initCsvPath))) {

            CsvToBean<Product> csvToBean = new CsvToBeanBuilder<Product>(reader)
                    .withType(Product.class)
                    .build();
            csvToBean.iterator().forEachRemaining(p -> trie.add(p, p.getId()));

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Csv file not found by path: " + initCsvPath);
        }
    }

    public void start() {
        greeting();

        List<Product> basket = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean done = false;
            while (!done) {
                try {
                    String input = scanner.next();
                    if (input.equals("exit")) {
                        done = true;
                        continue;
                    }

                    if (input.equals("sum")) {
                        processBasket(basket, scanner);
                        basket.clear();
                    } else {
                        Product product = getProduct(input);
                        if (product != null) {
                            basket.add(product);
                        }
                    }

                    println("ID or command: ");

                } catch (Throwable t) {
                    error(t.getMessage());
                    println("ID or command: ");
                }
            }
        }
    }

    private Product getProduct(String input) {
        if (input == null) {
            return null;
        }

        byte[] prefix = stringToByteArrayConverter.convert(input);

        final List<Product> products = trie.findAllByPrefix(prefix);
        final int productsSize = products.size();

        Product product = null;
        if (productsSize == 0) {
            println("Products not found!\n");
        } else if (productsSize == 1) {
            product = products.get(0);

            println(String.format(
                    "Product name: %s, price: %s\n",
                    product.getName(), product.getPrice()
            ));
        } else {
            println("Select one of: ");
            products.forEach(p -> println(p.getIdAsString()));

            System.out.println();
        }

        return product;
    }

    private void processBasket(List<Product> basket, Scanner scanner) {
        if (basket.isEmpty()) {
            println("No products yet!");
            return;
        }

        Map<String, Double> sum = calculateSum(basket);
        printBasketSum(sum);

        boolean done = false;
        while (!done) {
            try {
                Double amount = scanner.nextDouble();
                if (amount < sum.get("total")) {
                    println("Not enough!");
                    continue;
                }

                printReceipt(basket, sum, amount);
                done = true;

            } catch (Exception e) {
                println("Incorrect amount!",
                        "Enter a number in the format: ###,##");
                scanner.next();
            }
        }
    }

    private Map<String, Double> calculateSum(List<Product> products) {
        double subtotal = 0.0;
        double cityTaxTotal = 0.0;
        double stateTaxTotal = 0.0;
        double countryTaxTotal = 0.0;

        for (Product p : products) {
            Double price = p.getPrice().doubleValue();

            subtotal += price;

            cityTaxTotal += price * TAXES.get("city");
            if (p.getTaxType() != TaxType.G) {
                stateTaxTotal += price * TAXES.get("state");
                countryTaxTotal += price * TAXES.get("country");
            }
        }

        double taxTotal = cityTaxTotal + stateTaxTotal + countryTaxTotal;

        Map<String, Double> sum = new HashMap<>();
        sum.put("subtotal", subtotal);
        sum.put("cityTaxTotal", cityTaxTotal);
        sum.put("stateTaxTotal", stateTaxTotal);
        sum.put("countryTaxTotal", countryTaxTotal);
        sum.put("taxTotal", taxTotal);
        sum.put("total", subtotal + taxTotal);

        return sum;
    }
}
