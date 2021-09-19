package ch.addere.osv.domain.model.fields;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Details describes additional details about a vulnerability.
 */
@Value
@Accessors(fluent = true)
public class Details implements Jsonable {

  String details;

  /**
   * Create a details object from a string.
   *
   * @param details describing the vulnerability more detailed
   * @return an instance of a parsed detail description
   */
  public static Details create(@NonNull String details) {
    return new Details(details);
  }

  @Override
  public String toJson() {
    return details.replace("\\n", "\n");
  }
}
