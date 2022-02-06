package ch.addere.osvs.lib.field;

import ch.addere.osvs.lib.field.types.RangeEntry;
import ch.addere.osvs.lib.impl.RangeFieldImpl;

import java.util.List;

/** These entries descripe the affected ranges of versions. */
public interface RangesField extends EntryField<List<RangeEntry>> {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param rangeEntries entries for this specific range
   * @return valid {@code EventsField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static RangesField of(RangeEntry... rangeEntries) {
    return RangeFieldImpl.of(rangeEntries);
  }
}
