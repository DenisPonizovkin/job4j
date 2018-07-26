package ru.job4j.multithread.cache;

import java.util.concurrent.ConcurrentHashMap;

public class NonblockingCache {

     public static class OptimisticException extends RuntimeException {
     }

     private final ConcurrentHashMap<Integer, Base> data = new ConcurrentHashMap<Integer, Base>();

     public void add(Integer id, Base model) {
          data.put(id, model);
     }

     public void update(Base model) throws OptimisticException {
          if (data.containsValue(model)) {
               data.forEach((k, v) -> {
                    if (v.equals(model)) {
                         if (model.getVersion() != v.getVersion()) {
                              throw new OptimisticException();
                         }
                         v.update();
                    }
               });
          }
     }

     public void delete(Base model) {
          data.remove(model);
     }

     public int size() {
          return data.size();
     }
}
