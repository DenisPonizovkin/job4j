package ru.job4j.multithread.search;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ParallelSearchTest {

    //@Test
    public void whenStartThenFindAllFiles() {
        List<String> exts = new ArrayList<String>();
        exts.add("csv");
        exts.add("txt");
        ParallelSearch ps = new ParallelSearch(
                "/home/denis/tmp/test/",
                "text",
                exts);
        ps.init();
        //while (!ps.complete()) {
        //    try {
        //        Thread.sleep(100);
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //}
        assertThat(ps.result().size(), is(2));
    }

}
