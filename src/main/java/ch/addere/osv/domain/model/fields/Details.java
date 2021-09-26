package ch.addere.osv.domain.model.fields;

import lombok.Value;
import lombok.experimental.Accessors;

/**
 * Details describes additional details about a vulnerability.
 */
@Value
@Accessors(fluent = true)
public class Details {

  public static final String DETAILS_KEY = "details";

  @SuppressWarnings("PMD.AvoidFieldNameMatchingTypeNameRule")
  String details;

}
