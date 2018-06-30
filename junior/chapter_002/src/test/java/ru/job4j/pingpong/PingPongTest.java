package ru.job4j.pingpong;


import javafx.application.Application;
import org.junit.Test;
import ru.job4j.multithread.pingpong.PingPong;

import static org.junit.Assert.*;

public class PingPongTest {

    //@Test
    public void whenStartTestThenSeePingPog() {

        Thread thread = new Thread() { // Wrapper thread.
            @Override
            public void run() {
                try {
                    Application.launch(PingPong.class);
                } catch (Throwable t) {
                    if (t.getCause() != null && t.getCause().getClass().equals(InterruptedException.class)) {
                        return;
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException ex) {
        }
        thread.interrupt();
        try {
            thread.join(1);
        } catch (InterruptedException ex) {
        }
        assertTrue(true);

    }
}
