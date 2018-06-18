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

    private Iterator<Integer> current = null;

    public Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                if (current == null) {
                   current = it.next();
                }
                boolean has = current.hasNext();
                if (!current.hasNext()) {
                    while (it.hasNext()) {
                        if (current.hasNext()) {
                            has = true;
                            break;
                        }
                        current = it.next();
                    }
                }
                return has;
            }
            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return current.next();
            }
        };
    }
}
