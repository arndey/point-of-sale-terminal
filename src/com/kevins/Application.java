package com.kevins;

import com.kevins.product.application.Terminal;

public class Application {

    private static final Terminal terminal;

    static {
        terminal = Terminal.getInstance();
    }

    public static void main(String[] args) {

        System.out.println("\n\n***************************");
        System.out.println("*** Terminal loading... ***");
        terminal.initTrie(args[0]);

        System.out.println("***                     ***");
        System.out.println("*** Terminal are ready! ***");
        System.out.println("***************************\n\n");
        terminal.start();
    }
}
