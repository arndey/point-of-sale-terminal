package com.kevins;

import com.kevins.product.infrastructure.DecimalTrie;
import sun.text.normalizer.IntTrie;
import sun.text.normalizer.Trie;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        DecimalTrie trie = DecimalTrie.create();

//        Scanner scanner = new Scanner(System.in);
//
//        try (Reader reader = Files.newBufferedReader(Paths.get(args[0]))) {
//
//            CsvToBean<Product> csvToBean = new CsvToBeanBuilder<Product>(reader)
//                    .withType(Product.class)
//                    .withIgnoreLeadingWhiteSpace(true)
//                    .build();
//
//            List<Product> products = csvToBean.parse();
//            System.out.println("Enter product identifier: ");
//            String id = scanner.next();
//
//            try {
//                Long.parseLong(id);
//            } catch (NumberFormatException e) {
//                // TODO
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();  // TODO
//        }
    }
}
