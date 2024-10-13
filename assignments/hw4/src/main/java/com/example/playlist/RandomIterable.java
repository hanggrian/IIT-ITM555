package com.example.playlist;

import static com.google.common.base.Preconditions.checkArgument;

import androidx.annotation.NonNull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A list that retrieves item in a randomized index but never duplicate values consecutively.
 * Because there is a requirement of no duplicate, excess elements are filtered out in the
 * {@link #actualList}.
 */
public final class RandomIterable<E> implements Iterable<E> {
    private final List<E> actualList;

    private RandomIterable(@NonNull List<E> list) {
        actualList =
            list
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @NonNull
    @Override
    public Iterator<E> iterator() {
        return new RandomIterator();
    }

    /**
     * Creates a random-access list from the specified array.
     *
     * @param array the array by which the list will be backed.
     */
    @NonNull
    public static <E> RandomIterable<E> of(@NonNull E... array) {
        checkArgument(array.length > 0, "Input array cannot be empty.");
        return new RandomIterable<>(Arrays.asList(array));
    }

    private final class RandomIterator implements Iterator<E> {
        private final Random random = new Random();
        private final Set<Integer> pastIndices = new HashSet<>();

        @Override
        public boolean hasNext() {
            return actualList.size() > pastIndices.size();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Items are fixed-length.");
        }

        @Override
        public E next() {
            int index = random.nextInt(actualList.size());
            while (!pastIndices.add(index)) {
                index = random.nextInt(actualList.size());
            }
            return actualList.get(index);
        }
    }
}
