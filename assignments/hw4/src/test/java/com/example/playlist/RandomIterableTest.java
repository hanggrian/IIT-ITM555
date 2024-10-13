package com.example.playlist;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.Iterator;
import org.junit.Test;

public class RandomIterableTest {
    @Test
    public void emptyInput() {
        assertThrows(IllegalArgumentException.class, RandomIterable::of);
    }

    @Test
    public void unchangedTotalWithIterable() {
        int total = 0;
        for (int i : RandomIterable.of(1, 2, 3, 4, 5, 6, 7, 8, 9)) {
            total += i;
        }
        assertThat(total).isEqualTo(45);
    }

    @Test
    public void distinctValuesWithIterator() {
        Iterator<Integer> iterator = RandomIterable.of(3, 1, 2, 1, 2, 3).iterator();
        assertThat(iterator.next() + iterator.next() + iterator.next())
            .isEqualTo(6);
        assertThat(iterator.hasNext()).isFalse();
    }
}
