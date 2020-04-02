package com.kevins.product.application;

import com.kevins.product.domain.Product;
import com.kevins.product.domain.converters.StringToByteArrayConverter;
import com.kevins.product.domain.enums.TaxType;
import com.kevins.product.infrastructure.DecimalTrie;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Terminal {

    private static Terminal instance;

    private final DecimalTrie<Product> trie;

    private final StringToByteArrayConverter converter;  // TODO

    private static final Map<String, Double> TAXES;

    private static final String[] COMMANDS;

    static {
        COMMANDS = new String[]{"sum", "exit"};

        TAXES = new HashMap<String, Double>() {{
            put("city", 0.02);
            put("state", 0.063);
            put("country", 0.07);
        }};
    }

    private Terminal(DecimalTrie<Product> trie, StringToByteArrayConverter converter) {
        this.trie = trie;
        this.converter = converter;
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

        try (Scanner scanner = new Scanner(System.in)) {
            List<Product> basket = new ArrayList<>();

            while (true) {
                String input = scanner.next();
                if (input.equals("exit")) {
                    return;
                } else if (input.equals("sum")) {
                    Map<String, Double> sum = productsSum(basket);
                    println("Subtotal : " + sum.get("subtotal"),
                            "Total tax: " + sum.get("taxTotal"),
                            "Total    : " + sum.get("total"),
                            "\nCustomer amount: "
                    );

                    Double amount = scanner.nextDouble();

                    basket.clear();
                } else {
                    Product product = getProduct(input);
                    if (product != null) {
                        basket.add(product);
                    }
                }

                println("ID or command: ");
            }
        }
    }

    private Product getProduct(String input) {
        byte[] prefix = converter.convert(input);

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
            println("Which one do you mean?");
            products.forEach(p -> println(p.getIdAsString()));
            System.out.println();
        }

        return product;
    }

    private Map<String, Double> productsSum(List<Product> products) {
        Double subtotal = 0.0;
        Double taxTotal = 0.0;

        for (Product p : products) {
            Double price = p.getPrice().doubleValue();

            subtotal += price;

            taxTotal += price * TAXES.get("city");
            if (p.getTaxType() != TaxType.G) {
                taxTotal += price * TAXES.get("state");
                taxTotal += price * TAXES.get("country");
            }
        }

        Map<String, Double> sum = new HashMap<>();
        sum.put("subtotal", subtotal);
        sum.put("taxTotal", taxTotal);
        sum.put("total", subtotal + taxTotal);

        return sum;
    }

    private void greeting() {
        final String startInfo = String.format(
                "Commands: %s or enter ID: ",
                StringUtils.join(COMMANDS, ", ")
        );
        println(startInfo);
    }

    private void println(String... args) {
        for (String s : args) {
            System.out.println("* " + s);
        }
    }
}
