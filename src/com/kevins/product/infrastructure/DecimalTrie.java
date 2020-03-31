package com.kevins.product.infrastructure;

import java.util.ArrayList;
import java.util.List;

// TODO: переименовать
public class DecimalTrie {

    private DecimalTrieNode root;

    public static DecimalTrie create() {
        return new DecimalTrie();
    }

    public void add(Object value, byte[] key) {
        if (root == null) {
            root = new DecimalTrieNode(key[0]);
        }

        DecimalTrieNode current = root;
        for (byte i = 1; i < key.length; i++) {
            DecimalTrieNode child = current.getChild(key[i]);
            if (child == null) {
                child = current.addIfAbsent(key[i]);
            }

            current = child;

            if (i == key.length - 1) {
                current.setFinal(true);
                current.setValue(value);
            }
        }
    }

    public List<Object> findAllByPrefix(byte[] prefix) {
        List<Object> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        DecimalTrieNode current = root;
        for (byte i = 0; i < prefix.length; i++) {

        }

        return new ArrayList<>();
    }
}
