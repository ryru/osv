package ch.addere.osv.fields;

import java.time.Instant;

/**
 * Published is the time an entry was published. Time is represented as an FRC3339-formatted
 * timestamp.
 */
public interface Published {

  Instant value();

}
