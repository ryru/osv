package ch.addere.osv.impl.fields;

import static ch.addere.osv.impl.fields.IdDatabaseValue.GO;
import static ch.addere.osv.impl.fields.IdDatabaseValue.OSV;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class IdAggregateTest {

  private static final Id ID_1 = Id.of(GO, "id1");
  private static final Id ID_2 = Id.of(GO, "id2");
  private static final Id ID_3 = Id.of(GO, "id3");

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> IdAggregate.of((Id[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument ids must not be null");
  }

  @Test
  void testInvalidEmptyArguments() {
    assertThatThrownBy(IdAggregate::of)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("no ID, at least one ID must be provided");
  }

  @Test
  void testOfValueObjectCreation() {
    IdAggregate idAggregate1 = IdAggregate.of(Id.of(GO, "enEntryId"));
    IdAggregate idAggregate2 = IdAggregate.of(idAggregate1.value().toArray(new Id[0]));
    assertThat(idAggregate1).isEqualTo(idAggregate2);
  }

  @Test
  void testValidArugment() {
    Id id1 = Id.of(GO, "anEntryId");
    IdAggregate alias = IdAggregate.of(id1);
    assertThat(alias).satisfies(a -> {
      assertThat(a.value().size()).isEqualTo(1);
      assertThat(a.value().get(0)).isEqualTo(id1);
    });
  }

  @Test
  void testValidArugments() {
    var id1 = Id.of(GO, "anEntryId");
    var id2 = Id.of(OSV, "anotherEntryId");
    var alias = IdAggregate.of(id1, id2);
    assertThat(alias).satisfies(a -> {
      assertThat(a.value().size()).isEqualTo(2);
      assertThat(a.value().get(0)).isEqualTo(id1);
      assertThat(a.value().get(1)).isEqualTo(id2);
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
      assertThat(a2.value()).containsExactlyInAnyOrder(ID_1, ID_2);
      assertThat(a2).isEqualTo(related1);
    });
  }

  @Test
  void testAddId() {
    var related1 = IdAggregate.of(ID_1, ID_2);
    var related2 = related1.add(ID_3);
    assertThat(related2).satisfies(a2 -> {
      assertThat(a2.value()).containsExactlyInAnyOrder(ID_1, ID_2, ID_3);
      assertThat(a2).isNotEqualTo(related1);
      assertThat(related1.value()).containsExactlyInAnyOrder(ID_1, ID_2);
    });
  }

  @Test
  void testRemoveMissingId() {
    var related1 = IdAggregate.of(ID_1, ID_2);
    var related2 = related1.remove(ID_3);
    assertThat(related2).satisfies(a2 -> {
      assertThat(a2.value()).containsExactlyInAnyOrder(ID_1, ID_2);
      assertThat(a2).isEqualTo(related1);
    });
  }

  @Test
  void testRemoveId() {
    var related1 = IdAggregate.of(ID_1, ID_2);
    var related2 = related1.remove(ID_1);
    assertThat(related2).satisfies(a2 -> {
      assertThat(a2.value()).containsExactly(ID_2);
      assertThat(a2).isNotEqualTo(related1);
      assertThat(related1.value()).containsExactlyInAnyOrder(ID_1, ID_2);
    });
  }

  @Test
  void testEquality() {
    IdAggregate idAggregate = IdAggregate.of(ID_1, ID_2);
    IdAggregate otherIdAggregate = IdAggregate.of(ID_1, ID_2);
    assertThat(idAggregate).satisfies(i -> {
      assertThat(i).isEqualTo(idAggregate);
      assertThat(i).isEqualTo(otherIdAggregate);
    });
  }

  @Test
  void testNonEquality() {
    IdAggregate idAggregate = IdAggregate.of(ID_1, ID_2);
    IdAggregate otherIdAggregate = IdAggregate.of(ID_2);
    assertThat(idAggregate).satisfies(i -> {
      assertThat(i).isNotEqualTo(null);
      assertThat(i).isNotEqualTo(otherIdAggregate);
    });
  }

  @Test
  void testHashCode() {
    IdAggregate idAggregate = IdAggregate.of(ID_1, ID_2);
    IdAggregate otherIdAggregate = IdAggregate.of(ID_1, ID_2);
    assertThat(idAggregate).hasSameHashCodeAs(otherIdAggregate);
  }
}
