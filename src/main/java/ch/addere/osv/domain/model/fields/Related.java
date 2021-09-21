package ch.addere.osv.domain.model.fields;

import java.util.List;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Related specifies a list of IDs of closely related vulnerabilities. E.g. same issue in alternate
 * ecosystems.
 */
@Value
@Accessors(fluent = true)
public class Related {

  public static final String RELATED = "related";

  List<Id> related;

  /**
   * Each related ID represents an open source vulnerability database entry.
   *
   * @param related a list of IDs
   */
  public Related(@NonNull List<Id> related) {
    if (related.isEmpty()) {
      throw new IllegalArgumentException("related must not be empty");
    }
    this.related = related;
  }
}
