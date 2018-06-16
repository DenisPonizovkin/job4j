package ru.job4j.iterator.converter;

import javax.print.attribute.IntegerSyntax;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Iterator-converter.
 *
 * @author Denis Ponizovkin
 */
public class Converter {

    private Iterator<Integer> embedIt = null;

    public Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                if ((embedIt == null) || (!embedIt.hasNext())) {
                    while (it.hasNext()) {
                        embedIt = it.next();
                        if (embedIt != null) {
                           if (embedIt.hasNext()) {
                               return true;
                           }
                        }
                    }
                    return false;
                }
                return true;
            }
            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return embedIt.next();
            }
        };
    }
}
