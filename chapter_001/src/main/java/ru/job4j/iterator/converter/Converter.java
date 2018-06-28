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

    public Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {

            private Iterator<Integer> current = it.next();

            @Override
            public boolean hasNext() {

                boolean has = current.hasNext();
                if (!has) {
                    while (it.hasNext()) {
                        current = it.next();
                        if (current.hasNext()) {
                            has = true;
                            break;
                        }
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
