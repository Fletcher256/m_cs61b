import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;


public class TestListGet {
    @Test
    public void TestNull() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.get(0)).isEqualTo(null);
        assertThat(lld1.get(-232)).isEqualTo(null);
        assertThat(lld1.get(6578)).isEqualTo(null);

    }

    @Test
    public void TestNormal() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.get(0)).isEqualTo(-2);
        assertThat(lld1.get(-232)).isEqualTo(null);
        assertThat(lld1.get(6578)).isEqualTo(null);
        assertThat(lld1.get(3)).isEqualTo(1);
        assertThat(lld1.get(5)).isEqualTo(null);
        assertThat(lld1.get(4)).isEqualTo(2);

        lld1.addLast(90);

        assertThat(lld1.get(5)).isEqualTo(90);
    }

}
