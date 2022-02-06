package ch.addere.osvs.lib.field;

import ch.addere.osvs.lib.field.types.RangeType;
import ch.addere.osvs.lib.impl.RangeTypeFieldImpl;

/** Specifies the type of this range entry. */
public interface RangeTypeField extends EntryField<RangeType> {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param type the type for this range
   * @return valid {@code EventsField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static RangeTypeField of(RangeType type) {
    return RangeTypeFieldImpl.of(type);
  }
}
