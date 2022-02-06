package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.EventsField;
import ch.addere.osvs.lib.field.RangeTypeField;
import ch.addere.osvs.lib.field.RangesField;
import ch.addere.osvs.lib.field.types.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RangeFieldImplTest {

  private static final RangeTypeField TYPE_SEMVER = RangeTypeField.of(RangeType.SEMVER);
  private static final RangeTypeField TYPE_ECOSYSTEM = RangeTypeField.of(RangeType.ECOSYSTEM);
  private static final EventsEntry event1 = EventsEntry.of(EventType.INTRODUCED, "1.0.0");
  private static final EventsEntry event2 = EventsEntry.of(EventType.INTRODUCED, "1.2.0");
  private static final EventsField events1 = EventsField.of(event1, event2);
  private static final EventsField events2 = EventsField.of(event2);
  private static final RangeEntryBuilder rangeEntryBuilder1 =
      RangeEntryBuilder.builder(TYPE_SEMVER, events1);
  private static final RangeEntryBuilder rangeEntryBuilder2 =
      RangeEntryBuilder.builder(TYPE_ECOSYSTEM, events2);
  private static final RangeEntry RANGE_ENTRY_1 = rangeEntryBuilder1.build();
  private static final RangeEntry RANGE_ENTRY_2 = rangeEntryBuilder2.build();

  @Test
  void testOfRangesFieldNull() {
    assertThatThrownBy(() -> RangesField.of((RangeEntry[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument rangeEntries must not be null");
  }

  @Test
  void testKeyValue() {
    RangesField type = RangesField.of(RANGE_ENTRY_2);
    assertThat(type)
        .satisfies(
            r -> {
              assertThat(r.getKey()).isEqualTo("ranges");
              assertThat(r.getValue()).isEqualTo(List.of(RANGE_ENTRY_2));
            });
  }

  @Test
  void testEquality() {
    RangesField type1 = RangesField.of(RANGE_ENTRY_1);
    RangesField type2 = RangesField.of(RANGE_ENTRY_1);
    assertThat(type1)
        .satisfies(
            r -> {
              assertThat(r).isEqualTo(type1);
              assertThat(r).isEqualTo(type2);
            });
  }

  @Test
  void testNonEquality() {
    RangesField type1 = RangesField.of(RANGE_ENTRY_1);
    RangesField type2 = RangesField.of(RANGE_ENTRY_2);
    assertThat(type1)
        .satisfies(
            r -> {
              assertThat(r).isNotEqualTo(null);
              assertThat(r).isNotEqualTo(type2);
            });
  }

  @Test
  void testHashCode() {
    RangesField type1 = RangesField.of(RANGE_ENTRY_1);
    RangesField type2 = RangesField.of(RANGE_ENTRY_1);
    assertThat(type1).hasSameHashCodeAs(type2);
  }

  @Test
  void testToString() {
    RangesField type = RangesField.of(RANGE_ENTRY_1);
    assertThat(type)
        .hasToString(
            "ranges: [type: SEMVER\n" + "events: [ (introduced: 1.0.0), (introduced: 1.2.0) ]]");
  }
}
