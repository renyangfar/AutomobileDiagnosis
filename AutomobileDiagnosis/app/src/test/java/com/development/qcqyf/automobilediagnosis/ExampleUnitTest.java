package com.development.qcqyf.automobilediagnosis;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void test1(){
        String data = "carID:15296002587";
        if (data.contains("carID")) {
            String[] carIDStr = data.split(":");
            String carID = carIDStr[1];
            System.out.println("carID:" + carID);
        }

    }

}
