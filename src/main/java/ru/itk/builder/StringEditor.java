package ru.itk.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class StringEditor {
    private char[] charSequence;
    private int usedChars;
    private final List<Snapshot> snapshotHistory;

    public StringEditor() {
        this.charSequence = new char[16];
        this.usedChars = 0;
        this.snapshotHistory = new ArrayList<>();
    }

    public StringEditor(int capacity) {
        this.charSequence = new char[capacity];
        this.usedChars = 0;
        this.snapshotHistory = new ArrayList<>();
    }

    public StringEditor(String str) {
        int length = str.length();
        int capacity = (length < Integer.MAX_VALUE - 16)
                ? length + 16 : Integer.MAX_VALUE;
        this.charSequence =  new char[capacity];
        this.snapshotHistory = new ArrayList<>();
        append(str);
    }

    public StringEditor append(String str) {
        if (str == null) {
            return appendNull();
        }
        addSequenceToInnerCharSequence(str);
        return this;
    }

    public StringEditor undo() {
        if (snapshotHistory.size() > 1) {
            snapshotHistory.remove(snapshotHistory.size() - 1);
            Snapshot snapshot = snapshotHistory.get(snapshotHistory.size() - 1);
            changeCurrentState(snapshot);
        } else if (snapshotHistory.size() == 1) {
            snapshotHistory.remove(0);
            makeEmptyFields();
        }
        return this;
    }


    private StringEditor appendNull() {
        String str = "null";
        addSequenceToInnerCharSequence(str);
        return this;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > charSequence.length) {
            expandCapacity(minCapacity);
        }
    }

    private void expandCapacity(int minCapacity) {
        int newCapacity = Math.max(charSequence.length * 2, minCapacity);
        charSequence = Arrays.copyOf(charSequence, newCapacity);
    }

    private void addSequenceToInnerCharSequence(String str) {
        int newUsesChars = usedChars + str.length();
        ensureCapacity(newUsesChars);
        str.getChars(0, str.length(), charSequence, usedChars);
        usedChars = newUsesChars;
        saveState();
    }

    private Snapshot createSnapShot() {
        return new Snapshot(Arrays.copyOf(charSequence, charSequence.length), usedChars);
    }

    private void saveState() {
        snapshotHistory.add(createSnapShot());
    }

    private void changeCurrentState(Snapshot snapshot) {
        this.charSequence = snapshot.getCharSequence();
        this.usedChars = snapshot.getUsedChars();
    }

    private void makeEmptyFields() {
        String emptyStr = "";
        this.usedChars = 0;
        emptyStr.getChars(0, 0, charSequence, usedChars);
    }

    @Override
    public String toString() {
        if (charSequence.length == 0) {
            return "";
        }
        return new String(Arrays.copyOf(charSequence, usedChars));
    }

    @AllArgsConstructor
    @Getter
    private class Snapshot {
        private char[] charSequence;
        private int usedChars;
    }
}
