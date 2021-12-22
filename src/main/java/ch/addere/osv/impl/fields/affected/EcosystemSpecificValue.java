package ch.addere.osv.impl.fields.affected;

import ch.addere.osv.fields.Value;
import java.util.Objects;

/**
 * Ecosystem Specific property.
 */
public final class EcosystemSpecificValue implements Value<String> {

  public static final String ECOSYSTEM_SPECIFIC_KEY = "ecosystem_specific";

  private final String value;

  private EcosystemSpecificValue(String value) {
    Objects.requireNonNull(value, "argument ecosystem specific must not be null");
    this.value = value;
  }

  public static EcosystemSpecificValue fromString(String ecosystemSpecific) {
    return new EcosystemSpecificValue(ecosystemSpecific);
  }

  @Override
  public String value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EcosystemSpecificValue that = (EcosystemSpecificValue) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return ECOSYSTEM_SPECIFIC_KEY + ": " + value;
  }
}
