package com.example.picgenerator;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testGood() throws IOException {
        APIUtilizer.postRequest("n0lvFmxEqnBKWOGerXwbpddTzowNetpp", "nHt2WReR1iCt2HpMFfnQl0cjbWVkHMdv");
    }
}