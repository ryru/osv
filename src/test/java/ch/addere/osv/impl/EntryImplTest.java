package ch.addere.osv.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.Entry;
import ch.addere.osv.impl.EntryImpl.EntryBuilder;
import ch.addere.osv.impl.fields.IdDatabaseValue;
import ch.addere.osv.impl.fields.IdImpl;
import ch.addere.osv.impl.fields.ModifiedValue;
import ch.addere.osv.impl.fields.PublishedValue;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class EntryImplTest {

  private static Entry minimalEntry() {
    return entryBuilder().build();
  }

  private static EntryBuilder entryBuilder() {
    return EntryImpl.builder(
        IdImpl.of(IdDatabaseValue.GO, "2021-99999"),
        ModifiedValue.of(Instant.parse("2021-03-10T23:20:53Z")));
  }

  @Test
  void testOfAllNull() {
    assertThatThrownBy(() -> EntryImpl.builder(null, null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument id must not be null");
  }

  @Test
  void testOfIdNull() {
    assertThatThrownBy(() -> EntryImpl.builder(null, ModifiedValue.of(Instant.now())))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument id must not be null");
  }

  @Test
  void testOfModifiedNull() {
    assertThatThrownBy(() -> EntryImpl.builder(IdImpl.of(IdDatabaseValue.CVE, "anId"), null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument modified must not be null");
  }

  @Test
  void testEquality() {
    Entry entry = minimalEntry();
    Entry otherEntry = minimalEntry();
    assertThat(entry).isEqualTo(otherEntry);
  }

  @Test
  void testNonEquality() {
    Entry entry = minimalEntry();
    Entry otherEntry = entryBuilder().published(
        PublishedValue.of(Instant.parse("2019-04-11T20:21:53Z"))).build();
    assertThat(entry).isNotEqualTo(otherEntry);
  }

  @Test
  void testHashCode() {
    Entry entry = minimalEntry();
    Entry otherEntry = minimalEntry();
    assertThat(entry).hasSameHashCodeAs(otherEntry);
  }

  @Test
  void testMinimalToString() {
    Entry entry = minimalEntry();
    assertThat(entry).hasToString("id: GO, 2021-99999, modified: 2021-03-10T23:20:53Z");
  }
}
