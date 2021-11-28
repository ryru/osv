package ch.addere.osv.fields;

import java.time.Instant;

/**
 * Modified is the time an entry was last modified. Time is represented as an FRC3339-formatted
 * timestamp.
 */
public interface Modified {

  Instant value();

}
