package com.example.marks;

import com.example.marks.db.MarkDatabase;
import com.example.marks.db.Marks;
import com.example.marks.db.schema.Mark;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.internal.DoNotInstrument;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class MarksTest {
    private MarkDatabase db;

    @Before
    public void setup() {
        db =
            MarkDatabase.from(
                Robolectric.buildActivity(MainActivity.class).setup().get().getApplicationContext(),
                true
            );
    }

    @Test
    public void test() {
        Marks marks = db.marks();
        marks.deleteAll();

        // create
        Mark mark =
            new Mark.Builder()
                .id(0)
                .name("John Doe")
                .value('A')
                .build();
        marks.insertAll(mark);
        assertThat(marks.getCount())
            .isEqualTo(1);

        // read
        assertThat(mark.getId())
            .isEqualTo(0);
        assertThat(mark.getName())
            .isEqualTo("John Doe");
        assertThat(mark.getValue())
            .isEqualTo('A');

        // update
        mark.setId(1);
        mark.setName("Jane Doe");
        mark.setValue('B');
        marks.update(mark);
        assertThat(mark.getId())
            .isEqualTo(1);
        assertThat(mark.getName())
            .isEqualTo("Jane Doe");
        assertThat(mark.getValue())
            .isEqualTo('B');

        // delete
        marks.delete(mark);
        assertThat(marks.isExist(1))
            .isFalse();
    }

    @After
    public void finish() {
        db.close();
    }
}
