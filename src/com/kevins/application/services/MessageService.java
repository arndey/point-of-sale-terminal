package com.kevins.application.services;

import com.kevins.domain.Product;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MessageService {

    private static final DecimalFormat df;
    private static final String[] COMMANDS;

    static {
        df = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.US);

        COMMANDS = new String[]{"sum", "exit"};
    }

    public static void greeting() {
        final String startInfo = String.format(
                "Commands: %s or enter ID: ",
                StringUtils.join(COMMANDS, ", ")
        );
        println(startInfo);
    }

    public static void printBasketSum(Map<String, Double> sum) {
        println("Subtotal : " + df.format(sum.get("subtotal")),
                "Total tax: " + df.format(sum.get("taxTotal")),
                "Total    : " + df.format(sum.get("total")) + "\n",
                "Customer amount: "
        );
    }

    public static void printReceipt(List<Product> basket,
                                    Map<String, Double> sum,
                                    Double amount) {
        basket.forEach(p -> println(p.toString()));
        System.out.println();

        println("======== RECEIPT ========",
                "Subtotal   : " + df.format(sum.get("subtotal")),
                "City tax   : " + df.format(sum.get("cityTaxTotal")),
                "State tax  : " + df.format(sum.get("stateTaxTotal")),
                "Country tax: " + df.format(sum.get("countryTaxTotal")),
                "Total due  : " + df.format(sum.get("total")),
                "Amount paid: " + df.format(amount),
                "Change due : " + df.format(amount - sum.get("total")),
                "=========================\n"
        );
    }

    public static void println(String... args) {
        for (String s : args) {
            System.out.println("* " + s);
        }
    }

    public static void error(String msg) {
        System.out.println(
                String.format("Unexpected error: %s", msg)
        );
    }
}
