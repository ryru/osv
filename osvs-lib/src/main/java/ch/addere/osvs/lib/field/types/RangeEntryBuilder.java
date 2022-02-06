package ch.addere.osvs.lib.field.types;

import ch.addere.osvs.lib.field.EventsField;
import ch.addere.osvs.lib.field.RangeTypeField;
import ch.addere.osvs.lib.field.RepoField;
import ch.addere.osvs.lib.impl.RangeEntryBuilderImpl;

/** Range entry builder. */
public interface RangeEntryBuilder {

  /**
   * Set a repo to the builder.
   *
   * @param repo repository to add
   * @return range entry builder
   */
  RangeEntryBuilder setRepo(RepoField repo);

  /**
   * Build a {@link ch.addere.osvs.lib.field.types.RangeType} from this builder.
   *
   * @return a range entry
   */
  RangeEntry build();

  /**
   * Create a builder.
   *
   * @param type type to use in this builder
   * @param events events to use in this builder
   * @return range entry builder
   */
  static RangeEntryBuilder builder(RangeTypeField type, EventsField events) {
    return RangeEntryBuilderImpl.of(type, events);
  }
}
