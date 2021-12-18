package ch.addere.osv.fields.affected;

import ch.addere.osv.fields.affected.ranges.Event;
import ch.addere.osv.fields.affected.ranges.RangeType;
import ch.addere.osv.fields.affected.ranges.Repo;
import java.util.List;
import java.util.Optional;

/**
 * Ranges specify the versions the vulnerability affects on.
 */
public interface Ranges {

  String RANGES_KEY = "ranges";

  RangeType type();

  Optional<Repo> repo();

  List<? extends Event> events();

}
