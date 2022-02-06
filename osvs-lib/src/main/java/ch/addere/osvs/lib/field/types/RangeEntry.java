package ch.addere.osvs.lib.field.types;

import ch.addere.osvs.lib.field.EventsField;
import ch.addere.osvs.lib.field.RangeTypeField;
import ch.addere.osvs.lib.field.RepoField;

import java.util.Optional;

/** Range entry. */
public interface RangeEntry {

  /**
   * Range type field.
   *
   * @return range type
   */
  RangeTypeField type();

  /**
   * The Repository.
   *
   * @return repository
   */
  Optional<RepoField> repo();

  /**
   * Event field.
   *
   * @return events
   */
  EventsField events();
}
