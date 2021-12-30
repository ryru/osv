package ch.addere.osv.property;

import static ch.addere.osv.property.IdDatabaseValue.GO;
import static ch.addere.osv.property.IdDatabaseValue.OSV;
import static ch.addere.osv.property.RelatedValue.RELATED_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class RelatedValueTest {


  private static final IdValue ID_1 = IdValue.of(GO, "id1");
  private static final IdValue ID_2 = IdValue.of(GO, "id2");
  private static final IdValue ID_3 = IdValue.of(GO, "id3");

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> RelatedValue.of((IdValue[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument ids must not be null");
  }

  @Test
  void testInvalidEmptyArguments() {
    assertThatThrownBy(RelatedValue::of)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("no ID, at least one ID must be provided");
  }

  @Test
  void testOfValueObjectCreation() {
    RelatedValue related1 = RelatedValue.of(IdValue.of(GO, "enEntryId"));
    RelatedValue related2 = RelatedValue.of(related1.value().toArray(new IdValue[0]));
    assertThat(related1).isEqualTo(related2);
  }

  @Test
  void testValidArugment() {
    IdValue id1 = IdValue.of(GO, "anEntryId");
    RelatedValue alias = RelatedValue.of(id1);
    assertThat(alias).satisfies(a -> {
      assertThat(a.value().size()).isEqualTo(1);
      assertThat(a.value().get(0)).isEqualTo(id1);
    });
  }

  @Test
  void testValidArugments() {
    var id1 = IdValue.of(GO, "anEntryId");
    var id2 = IdValue.of(OSV, "anotherEntryId");
    var alias = RelatedValue.of(id1, id2);
    assertThat(alias).satisfies(a -> {
      assertThat(a.value().size()).isEqualTo(2);
      assertThat(a.value().get(0)).isEqualTo(id1);
      assertThat(a.value().get(1)).isEqualTo(id2);
    });
  }

  @Test
  void testContainsId1() {
    var related = RelatedValue.of(ID_1, ID_2);
    boolean hasId1 = related.contains(ID_1);
    assertThat(hasId1).isTrue();
  }

  @Test
  void testContainsId2() {
    var related = RelatedValue.of(ID_1, ID_2);
    boolean hasId2 = related.contains(ID_2);
    assertThat(hasId2).isTrue();
  }

  @Test
  void testDoesNotContainsId3() {
    var related = RelatedValue.of(ID_1, ID_2);
    boolean noId3 = related.contains(ID_3);
    assertThat(noId3).isFalse();
  }

  @Test
  void testAddDuplicateId() {
    var related1 = RelatedValue.of(ID_1, ID_2);
    var related2 = related1.add(ID_1);
    assertThat(related2).satisfies(a2 -> {
      assertThat(a2.value()).containsExactlyInAnyOrder(ID_1, ID_2);
      assertThat(a2).isEqualTo(related1);
    });
  }

  @Test
  void testAddId() {
    var related1 = RelatedValue.of(ID_1, ID_2);
    var related2 = related1.add(ID_3);
    assertThat(related2).satisfies(a2 -> {
      assertThat(a2.value()).containsExactlyInAnyOrder(ID_1, ID_2, ID_3);
      assertThat(a2).isNotEqualTo(related1);
      assertThat(related1.value()).containsExactlyInAnyOrder(ID_1, ID_2);
    });
  }

  @Test
  void testRemoveMissingId() {
    var related1 = RelatedValue.of(ID_1, ID_2);
    var related2 = related1.remove(ID_3);
    assertThat(related2).satisfies(a2 -> {
      assertThat(a2.value()).containsExactlyInAnyOrder(ID_1, ID_2);
      assertThat(a2).isEqualTo(related1);
    });
  }

  @Test
  void testRemoveId() {
    var related1 = RelatedValue.of(ID_1, ID_2);
    var related2 = related1.remove(ID_1);
    assertThat(related2).satisfies(a2 -> {
      assertThat(a2.value()).containsExactly(ID_2);
      assertThat(a2).isNotEqualTo(related1);
      assertThat(related1.value()).containsExactlyInAnyOrder(ID_1, ID_2);
    });
  }

  @Test
  void testEquality() {
    RelatedValue related = RelatedValue.of(ID_1, ID_2);
    RelatedValue otherRelatedValue = RelatedValue.of(ID_1, ID_2);
    assertThat(related).satisfies(i -> {
      assertThat(i).isEqualTo(related);
      assertThat(i).isEqualTo(otherRelatedValue);
    });
  }

  @Test
  void testNonEquality() {
    RelatedValue related = RelatedValue.of(ID_1, ID_2);
    RelatedValue otherRelatedValue = RelatedValue.of(ID_2);
    assertThat(related).satisfies(i -> {
      assertThat(i).isNotEqualTo(null);
      assertThat(i).isNotEqualTo(otherRelatedValue);
    });
  }

  @Test
  void testHashCode() {
    RelatedValue related = RelatedValue.of(ID_1, ID_2);
    RelatedValue otherRelatedValue = RelatedValue.of(ID_1, ID_2);
    assertThat(related).hasSameHashCodeAs(otherRelatedValue);
  }

  @Test
  void testToString() {
    RelatedValue related = RelatedValue.of(ID_1, ID_2);
    assertThat(related).hasToString(RELATED_KEY + ": " + join(", ",
        ID_1.value(),
        ID_2.value()));
  }
}
