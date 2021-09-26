package ch.addere.osv.domain.model.fields;

import java.time.Instant;

/**
 * Date representation of all date fields within the specification.
 */
public abstract class EntryDate {

  protected Instant date;

  protected Instant date() {
    return date;
  }
}
