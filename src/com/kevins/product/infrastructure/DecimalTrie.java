package com.kevins.product.infrastructure;

import java.util.ArrayList;
import java.util.List;

// TODO: переименовать
public class DecimalTrie<T> {

    private DecimalTrieNode<T> root;

    public DecimalTrie() {}

    public void add(T value, byte[] key) {
        if (root == null) {
            root = new DecimalTrieNode<>(key[0]);
        }

        DecimalTrieNode<T> current = root;
        for (byte i = 1; i < key.length; i++) {
            DecimalTrieNode<T> child = current.getChild(key[i]);
            if (child == null) {
                child = current.addChild(key[i]);
            }

            current = child;

            if (i == key.length - 1) {
                current.setFinal(true);
                current.setValue(value);
            }
        }
    }

    public List<T> findAllByPrefix(byte[] prefix) {
        List<T> values = new ArrayList<>();

        if (root == null) {
            return values;
        }

        DecimalTrieNode<T> current = root;
        for (byte i : prefix) {
            DecimalTrieNode<T> child = current.getChild(i);
            if (child == null) {
                break;
            }

            current = child;
        }

        findAllValues(values, current);  // TODO: мб перенести и переименовать

        return values;
    }

    private void findAllValues(List<T> values, DecimalTrieNode<T> current) {
        for (DecimalTrieNode<T> child : current.getChildren().values()) {
            if (child.isFinal()) {
                values.add(child.getValue());
            }

            findAllValues(values, child);
        }
    }
}
