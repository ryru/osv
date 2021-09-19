package ch.addere.osv.domain.model.fields;

import java.util.List;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Aliases is a list of IDs of the same vulnerability in other databases.
 */
@Value
@Accessors(fluent = true)
public class Aliases {

  public static final String ALIASES = "aliases";

  List<Id> aliases;

  /**
   * Each alias ID represents an open source vulnerability database entry.
   *
   * @param aliases a list of IDs
   */
  public Aliases(@NonNull List<Id> aliases) {
    if (aliases.isEmpty()) {
      throw new IllegalArgumentException("aliases must not be empty");
    }
    this.aliases = aliases;
  }
}
