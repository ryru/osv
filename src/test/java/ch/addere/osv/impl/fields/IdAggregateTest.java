package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.IdImpl.Database.GO;
import static ch.addere.osv.impl.fields.IdImpl.Database.OSV;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.fields.Id;
import org.junit.jupiter.api.Test;

class IdAggregateTest {

  private static final Id ID_1 = IdImpl.of(GO, "id1");
  private static final Id ID_2 = IdImpl.of(GO, "id2");
  private static final Id ID_3 = IdImpl.of(GO, "id3");

  @Test
  void testInvalidEmptyArguments() {
    assertThatThrownBy(IdAggregate::of)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("no ID, at least one ID must be provided");
  }

  @Test
  void testValidArugment() {
    var id1 = IdImpl.of(GO, "anEntryId");
    var alias = IdAggregate.of(id1);
    assertThat(alias).satisfies(a -> {
      assertThat(a.ids().size()).isEqualTo(1);
      assertThat(a.ids().get(0)).isEqualTo(id1);
    });
  }

  @Test
  void testValidArugments() {
    var id1 = IdImpl.of(GO, "anEntryId");
    var id2 = IdImpl.of(OSV, "anotherEntryId");
    var alias = IdAggregate.of(id1, id2);
    assertThat(alias).satisfies(a -> {
      assertThat(a.ids().size()).isEqualTo(2);
      assertThat(a.ids().get(0)).isEqualTo(id1);
      assertThat(a.ids().get(1)).isEqualTo(id2);
    });
  }

  @Test
  void testContainsId1() {
    var related = IdAggregate.of(ID_1, ID_2);
    boolean hasId1 = related.contains(ID_1);
    assertThat(hasId1).isTrue();
  }

  @Test
  void testContainsId2() {
    var related = IdAggregate.of(ID_1, ID_2);
    boolean hasId2 = related.contains(ID_2);
    assertThat(hasId2).isTrue();
  }

  @Test
  void testDoesNotContainsId3() {
    var related = IdAggregate.of(ID_1, ID_2);
    boolean noId3 = related.contains(ID_3);
    assertThat(noId3).isFalse();
  }

  @Test
  void testAddDuplicateId() {
    var related1 = IdAggregate.of(ID_1, ID_2);
    var related2 = related1.add(ID_1);
    assertThat(related2).satisfies(a2 -> {
      assertThat(a2.ids()).containsExactlyInAnyOrder(ID_1, ID_2);
      assertThat(a2).isEqualTo(related1);
    });
  }

  @Test
  void testAddId() {
    var related1 = IdAggregate.of(ID_1, ID_2);
    var related2 = related1.add(ID_3);
    assertThat(related2).satisfies(a2 -> {
      assertThat(a2.ids()).containsExactlyInAnyOrder(ID_1, ID_2, ID_3);
      assertThat(a2).isNotEqualTo(related1);
      assertThat(related1.ids()).containsExactlyInAnyOrder(ID_1, ID_2);
    });
  }

  @Test
  void testRemoveMissingId() {
    var related1 = IdAggregate.of(ID_1, ID_2);
    var related2 = related1.remove(ID_3);
    assertThat(related2).satisfies(a2 -> {
      assertThat(a2.ids()).containsExactlyInAnyOrder(ID_1, ID_2);
      assertThat(a2).isEqualTo(related1);
    });
  }

  @Test
  void testRemoveId() {
    var related1 = IdAggregate.of(ID_1, ID_2);
    var related2 = related1.remove(ID_1);
    assertThat(related2).satisfies(a2 -> {
      assertThat(a2.ids()).containsExactly(ID_2);
      assertThat(a2).isNotEqualTo(related1);
      assertThat(related1.ids()).containsExactlyInAnyOrder(ID_1, ID_2);
    });
  }
}
