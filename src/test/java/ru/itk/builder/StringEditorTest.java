package ru.itk.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringEditorTest {
    private StringEditor editor;

    @BeforeEach
    public void setUp() {
        editor = new StringEditor();
    }

    @Test
    public void testConstructorWithDefaultCapacity() {
        assertEquals("", editor.toString());
    }

    @Test
    public void testConstructorWithSpecifiedCapacity() {
        int customCapacity = 32;
        StringEditor customEditor = new StringEditor(customCapacity);
        assertEquals("", customEditor.toString());
    }

    @Test
    public void testConstructorWithString() {
        String str = "Hello, World!";
        StringEditor stringEditor = new StringEditor(str);
        assertEquals(str, stringEditor.toString());
    }

    @Test
    public void testAppend() {
        editor.append("Hello");
        assertEquals("Hello", editor.toString());

        editor.append(" World!");
        assertEquals("Hello World!", editor.toString());
    }

    @Test
    public void testAppendNull() {
        editor.append(null);
        assertEquals("null", editor.toString());
    }

    @Test
    public void testUndo() {
        editor.undo();
        assertEquals("", editor.toString());

        editor.append("Hello");
        editor.append(" World!");
        assertEquals("Hello World!", editor.toString());

        editor.undo();
        assertEquals("Hello", editor.toString());

        editor.undo();
        assertEquals("", editor.toString());

        editor.undo();
        assertEquals("", editor.toString());
    }

    @Test
    public void testCapacityExpansion() {
        String longString = "a".repeat(20);
        editor.append(longString);
        assertEquals(longString, editor.toString());

        editor.append(longString);
        String expected = longString + longString;
        assertEquals(expected, editor.toString());
    }

    @Test
    public void testSnapshotHistory() {
        editor.append("Hello");
        assertEquals("Hello", editor.toString());

        editor.append(" World!");
        assertEquals("Hello World!", editor.toString());

        editor.append(" Goodbye!");
        assertEquals("Hello World! Goodbye!", editor.toString());

        editor.undo();
        assertEquals("Hello World!", editor.toString());

        editor.undo();
        assertEquals("Hello", editor.toString());

        editor.undo();
        assertEquals("", editor.toString());

        editor.undo();
        assertEquals("", editor.toString());
    }

    @Test
    public void testToString() {
        assertEquals("", editor.toString());

        editor.append("Test");
        assertEquals("Test", editor.toString());

        editor.append(" String");
        assertEquals("Test String", editor.toString());
    }

}