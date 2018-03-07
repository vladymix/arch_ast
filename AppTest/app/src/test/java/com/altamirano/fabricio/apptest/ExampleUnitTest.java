package com.altamirano.fabricio.apptest;

import com.altamirano.fabricio.libraryast.Tools;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void collectionIsNull_isCorrect() throws Exception {
        assertTrue(Tools.isNullOrEmpty(new ArrayList<>()));
    }

    @Test
    public void stringIsNull_isCorrect() throws Exception {
        String vale =null;
        assertTrue(Tools.isNullOrEmpty(vale));
    }

    @Test
    public void stringIsEmpty_isCorrect() throws Exception {
        String vale ="";
        assertTrue(Tools.isNullOrEmpty(vale));
    }

    @Test
    public void stringIsEmptyTrim_isCorrect() throws Exception {
        String vale ="              ";
        assertTrue(Tools.isNullOrEmpty(vale));
    }

    @Test
    public void CharSequenceIsEmptyTrim_isCorrect() throws Exception {
        CharSequence vale = "              ";
        assertTrue(Tools.isNullOrEmpty(vale));
    }

    @Test
    public void CharSequenceIsNull_isCorrect() throws Exception {
        CharSequence vale = null;
        assertTrue(Tools.isNullOrEmpty(vale));
    }

    @Test
    public void CharSequenceIsEmpty_isCorrect() throws Exception {
        CharSequence vale = "";
        assertTrue(Tools.isNullOrEmpty(vale));
    }
}