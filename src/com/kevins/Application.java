package com.kevins;

import com.kevins.product.domain.Product;
import com.kevins.product.infrastructure.DecimalTrie;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import sun.text.normalizer.IntTrie;
import sun.text.normalizer.Trie;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Application {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        try (Reader reader = Files.newBufferedReader(Paths.get(args[0]))) {

            CsvToBean<Product> csvToBean = new CsvToBeanBuilder<Product>(reader)
                    .withType(Product.class)
                    .build();

            DecimalTrie<Product> trie = new DecimalTrie<>();
            csvToBean.iterator().forEachRemaining(p -> {
                trie.add(p, p.getId());
            });

            System.out.println("Enter product identifier: ");
            String id = scanner.next();

        } catch (IOException e) {
            e.printStackTrace();  // TODO
        }
    }
}
