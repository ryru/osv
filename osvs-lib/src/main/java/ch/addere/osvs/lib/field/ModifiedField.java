package ch.addere.osvs.lib.field;

import ch.addere.osvs.lib.impl.ModifiedFieldImpl;

import java.time.Instant;

/**
 * The {@code modified} field gives the time the entry was last modified, as an RFC3339-formatted
 * timestamp time stamp in UTC (ending in “Z”). Given two different entries claiming to describe the
 * same {@code id} field, the one with the later modification time is considered authoritative.
 */
public interface ModifiedField extends EntryField<Instant> {

  /**
   * Create a new instance valid according the latest specification version.
   *
   * @param modified last modification
   * @return valid {@code ModifiedField} instance
   * @throws IllegalArgumentException if arguments are invalid
   */
  static ModifiedField of(Instant modified) {
    return ModifiedFieldImpl.of(modified);
  }
}
