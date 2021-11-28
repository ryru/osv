package ch.addere.osv.domain.model.fields;

import java.util.Objects;

/**
 * Details describes additional details about a vulnerability.
 */
public final class Details {

  public static final String DETAILS_KEY = "details";

  private final String details;

  private Details(String details) {
    this.details = details;
  }

  public static Details of(String details) {
    return new Details(details);
  }

  public String value() {
    return details;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Details details1 = (Details) o;
    return Objects.equals(details, details1.details);
  }

  @Override
  public int hashCode() {
    return Objects.hash(details);
  }

  @Override
  public String toString() {
    return DETAILS_KEY + ": " + details;
  }
}
