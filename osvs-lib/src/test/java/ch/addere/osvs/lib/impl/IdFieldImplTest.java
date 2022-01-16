package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.IdField;
import org.junit.jupiter.api.Test;

import static ch.addere.osvs.lib.field.types.IdDb.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class IdFieldImplTest {

  private static final String ENTRY_ID = "2020-111";

  @Test
  void testOfDbNull() {
    assertThatThrownBy(() -> IdField.of(null, ENTRY_ID))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument db must not be null");
  }

  @Test
  void testOfEntryIdNull() {
    assertThatThrownBy(() -> IdField.of(GO, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument entryId must not be null");
  }

  @Test
  void testOfEmptyEntryId() {
    assertThatThrownBy(() -> IdField.of(GO, ""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("entryId must not be empty");
  }

  @Test
  void testJsonKeyValue() {
    IdField id = IdField.of(OSV, ENTRY_ID);
    assertThat(id)
        .satisfies(
            i -> {
              assertThat(i.getKey()).isEqualTo("id");
              assertThat(i.getValue()).isEqualTo("OSV-2020-111");
              assertThat(i.getEntryId()).isEqualTo("2020-111");
              assertThat(i.getDb()).isEqualTo(OSV);
            });
  }

  @Test
  void testEquality() {
    IdField id1 = IdField.of(OSV, ENTRY_ID);
    IdField id2 = IdField.of(OSV, ENTRY_ID);
    assertThat(id1)
        .satisfies(
            i -> {
              assertThat(i).isEqualTo(id1);
              assertThat(i).isEqualTo(id2);
            });
  }

  @Test
  void testNonEquality() {
    IdField id1 = IdField.of(OSV, ENTRY_ID);
    IdField id2 = IdField.of(GSD, ENTRY_ID);
    assertThat(id1)
        .satisfies(
            i -> {
              assertThat(id1).isNotEqualTo(null);
              assertThat(id1).isNotEqualTo(id2);
            });
  }

  @Test
  void testHashCode() {
    IdField id1 = IdField.of(OSV, ENTRY_ID);
    IdField id2 = IdField.of(OSV, ENTRY_ID);
    assertThat(id1).hasSameHashCodeAs(id2);
  }

  @Test
  void testToString() {
    IdField id = IdField.of(OSV, ENTRY_ID);
    assertThat(id).hasToString("id: OSV-2020-111");
  }
}
