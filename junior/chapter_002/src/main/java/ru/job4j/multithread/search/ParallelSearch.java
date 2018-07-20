package ru.job4j.multithread.search;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@ThreadSafe
public class ParallelSearch {
    private final String root;
    private final String text;
    private final List<String> exts;
    volatile boolean finish = false;
    boolean complete = false;

    @GuardedBy("this")
    private final Queue<String> files = new LinkedList<>();

    @GuardedBy("this")
    private final List<String> paths = new ArrayList<>();


    public ParallelSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
    }

    interface Operationable {
        boolean search(String path);
    }

    public void init() {
        System.out.println("Parallel search init");
        Thread search = new Thread() {
            @Override
            public void run() {
                Path dir = Paths.get(root);
                System.out.println("Check dir: " + dir.toString());
                synchronized (this) {
                    finish = false;
                    ExtensionFileVisitor efv = new ExtensionFileVisitor(exts, files);
                    try {
                        Files.walkFileTree(dir, efv);
                        finish = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread read = new Thread() {
            @Override
            public void run() {
                String path = "";
                boolean complete = false;
                while (true) {
                    synchronized (this) {
                        if (finish && files.size() == 0) {
                            break;
                        } else if (!finish) {
                            continue;
                        }
                        path = files.poll();
                    }
                    Operationable op = (String path2file) -> {
                        boolean is = false;
                        try {
                            File f = new File(path2file);
                            BufferedReader b = new BufferedReader(new FileReader(f));
                            String readLine = "";
                            while ((readLine = b.readLine()) != null) {
                                if (readLine.contains(text)) {
                                    paths.add(path2file);
                                    break;
                                }
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return is;
                    };
                    if (op.search(path)) {
                        synchronized (this) {
                            paths.add(path);
                            System.out.println("Complete");
                        }
                    }
                }
            }
        };
        search.start();
        read.start();

        try {
                search.join();
                while (search.isAlive()) {
                   Thread.sleep(10);
                   //complete = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized List<String> result() {
            System.out.println("Paths3 " + paths.size());
            return this.paths;
        }

        public boolean complete() {
            return complete;
        }
    }
