package ru.job4j.iterator.prime;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator which returns prime number.
 */
public class IteratorArrayPrimeNumbers implements Iterator {
    /**
     * Data.
     */
    private int[] array;
    /**
     * Current index.
     */
    private int index = 0;
    /**
     * If there is no prime number in array then isPrimeNumber() method return this value.
     */
    private final int noPrimeNumberId = -1;

    public IteratorArrayPrimeNumbers(int[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return primeNumberId() != -1;
    }

    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        for (++index; index < array.length; index++) {
            if (isPrimeNumber(array[index])) {
                return array[index];
            }
        }
        throw new NoSuchElementException();
    }

    /**
     *
     * @return id of first prime number after index.
     */
    private int primeNumberId() {
        for (int i = index + 1; i < array.length; i++) {
           if (isPrimeNumber(array[i])) {
               return i;
           }
        }
        return noPrimeNumberId;
    }

    /**
     *
     * @param n - integer number.
     * @return - return true if the n is prime, false otherwise.
     */
    private boolean isPrimeNumber(int n) {
        if (n == 2) {
            return true;
        }
        for (int divider = 2; divider <= n / 2; divider++) {
            if (n % divider == 0) {
                return false;
            }
        }
        return true;
    }
}
