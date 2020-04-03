package com.kevins.infrastructure;

import java.util.ArrayList;
import java.util.List;

public class DecimalTrie<T> {

    private DecimalTrieNode<T> root;

    public DecimalTrie() {
        root = new DecimalTrieNode<>();
    }

    public void add(T value, byte[] key) {
        DecimalTrieNode<T> current = root;

        for (int i = 0; i < key.length; i++) {
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

        DecimalTrieNode<T> current = root;

        for (byte p : prefix) {
            DecimalTrieNode<T> child = current.getChild(p);
            if (child == null) {
                current = null;
                break;
            }

            current = child;
        }

        allNodeValues(values, current);

        return values;
    }

    private void allNodeValues(List<T> values, DecimalTrieNode<T> current) {
        if (current == null) {
            return;
        } else if (current.isFinal()) {
            values.add(current.getValue());
        }

        for (DecimalTrieNode<T> child : current.getChildren().values()) {
            if (child.isFinal()) {
                values.add(child.getValue());
                return;
            }

            allNodeValues(values, child);
        }
    }
}
