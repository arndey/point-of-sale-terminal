package com.kevins.product.infrastructure;

import java.util.HashMap;
import java.util.Objects;

public class DecimalTrieNode {

    private byte keyPart;

    private Object value;

    private HashMap<Byte, DecimalTrieNode> children;

    private boolean isFinal;

    DecimalTrieNode(byte keyPart) {
        this.keyPart = keyPart;
        this.children = new HashMap<>();
        this.isFinal = false;
    }

    public DecimalTrieNode getChild(byte keyPart) {
        return children.get(keyPart);
    }

    public DecimalTrieNode addIfAbsent(DecimalTrieNode node) {
        return children.putIfAbsent(node.getKeyPart(), node);
    }

    public DecimalTrieNode addIfAbsent(byte keyPart) {
        return children.putIfAbsent(keyPart, new DecimalTrieNode(keyPart));
    }

    public DecimalTrieNode add(DecimalTrieNode node) {
        return children.put(node.getKeyPart(), node);
    }

    public DecimalTrieNode add(byte keyPart) {
        return children.put(keyPart, new DecimalTrieNode(keyPart));
    }

    // ====================================================================

    public byte getKeyPart() {
        return keyPart;
    }

    public void setKeyPart(byte keyPart) {
        this.keyPart = keyPart;
    }

    public Object getValue() {
        return value;
    }

    public HashMap<Byte, DecimalTrieNode> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Byte, DecimalTrieNode> children) {
        this.children = children;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public void setValue(Object value) {
        if (!isFinal) return;  // TODO: обдумать

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DecimalTrieNode)) return false;
        DecimalTrieNode that = (DecimalTrieNode) o;
        return getKeyPart() == that.getKeyPart() &&
                isFinal() == that.isFinal() &&
                Objects.equals(getValue(), that.getValue()) &&
                Objects.equals(getChildren(), that.getChildren());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKeyPart(), getValue(), getChildren(), isFinal());
    }

    @Override
    public String toString() {
        return "DecimalTrieNode{" +
                "keyPart=" + keyPart +
                ", value=" + value +
                ", children=" + children +
                ", isFinal=" + isFinal +
                '}';
    }
}