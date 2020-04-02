package com.kevins.product.infrastructure;

import java.util.HashMap;
import java.util.Objects;

// TODO: какой-то интерфейс для T чтобы чекать ид
public class DecimalTrieNode<T> {

    private byte keyPart;

    private T value;

    private HashMap<Byte, DecimalTrieNode<T>> children;

    private boolean isFinal;

    private boolean isRoot;

    DecimalTrieNode() {
        this.isRoot = true;
        this.children = new HashMap<>();
    }

    DecimalTrieNode(byte keyPart) {
        this.keyPart = keyPart;
        this.children = new HashMap<>();
    }

    public DecimalTrieNode<T> getChild(byte keyPart) {
        return children.get(keyPart);
    }

    public DecimalTrieNode<T> addChild(byte keyPart) {
        DecimalTrieNode<T> child = new DecimalTrieNode<>(keyPart);
        children.put(keyPart, child);

        return child;
    }

    // ====================================================================

    public byte getKeyPart() {
        return keyPart;
    }

    public void setKeyPart(byte keyPart) {
        this.keyPart = keyPart;
    }

    public HashMap<Byte, DecimalTrieNode<T>> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Byte, DecimalTrieNode<T>> children) {
        this.children = children;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (!isFinal) return;  // TODO: обдумать

        this.value = value;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DecimalTrieNode)) return false;
        DecimalTrieNode<?> that = (DecimalTrieNode<?>) o;
        return getKeyPart() == that.getKeyPart() &&
                isFinal() == that.isFinal() &&
                isRoot() == that.isRoot() &&
                Objects.equals(getValue(), that.getValue()) &&
                Objects.equals(getChildren(), that.getChildren());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKeyPart(), getValue(), getChildren(), isFinal(), isRoot());
    }

    @Override
    public String toString() {
        return "DecimalTrieNode{" +
                "keyPart=" + keyPart +
                ", value=" + value +
                ", children=" + children +
                ", isFinal=" + isFinal +
                ", isRoot=" + isRoot +
                '}';
    }
}