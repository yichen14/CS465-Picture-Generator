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
        String accessToken = APIUtilizer.getAccessTokenFromAPI("n0lvFmxEqnBKWOGerXwbpddTzowNetpp", "nHt2WReR1iCt2HpMFfnQl0cjbWVkHMdv");
        APIUtilizer.postPicGenRequest(accessToken, "睡莲", "油画");
    }

    @Test
    public void testBad() throws IOException {
        APIUtilizer.checkGenerationStatus("24.2dba9896ebc094847d7206345c6891a6.86400000.1668403240073.91935c2b97ec7bb7257d905d28c009dc-136892", "8050204");
    }
}