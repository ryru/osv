package ch.addere.osv.fields;

import java.time.Instant;

/**
 * Withdrawn is the time an entry should be considered to have been withdrawn. Time is represented
 * as an FRC3339-formatted timestamp.
 */
public interface Withdrawn {

  Instant value();

}
