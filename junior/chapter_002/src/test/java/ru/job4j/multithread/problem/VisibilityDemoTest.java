package ru.job4j.multithread.problem;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class VisibilityDemoTest {
    @Test
    public void whenNoSyncVisibilitiProblemExists() {

        VisibilityDemo vid = new VisibilityDemo();
        Thread th = new Thread(vid);
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("v1 = " + vid.getThread1Value() + ", v2 = " + vid.getThread2Value());

        assertThat(vid.getThread1Value() == vid.getThread2Value(), is(false));
    }

}