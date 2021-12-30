package ch.addere.osv.property.affected;

import ch.addere.osv.property.affected.ranges.Event;
import ch.addere.osv.property.affected.ranges.RangeTypeValue;
import ch.addere.osv.property.affected.ranges.RepoValue;
import java.util.List;
import java.util.Optional;

/**
 * Ranges specify the versions the vulnerability affects on.
 */
public interface Ranges {

  String RANGES_KEY = "ranges";

  RangeTypeValue type();

  Optional<RepoValue> repo();

  List<Event> events();

}
