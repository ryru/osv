package ch.addere.osv.domain.model.fields.affected;

import java.util.Objects;

/**
 * Ecosystem Specific property.
 */
public final class EcosystemSpecific {

  public static final String ECOSYSTEM_SPECIFIC_KEY = "ecosystem_specific";

  private final String ecosystemSpecific;

  private EcosystemSpecific(String ecosystemSpecific) {
    this.ecosystemSpecific = ecosystemSpecific;
  }

  public static EcosystemSpecific of(String ecosystemSpecific) {
    return new EcosystemSpecific(ecosystemSpecific);
  }

  public String value() {
    return ecosystemSpecific;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EcosystemSpecific that = (EcosystemSpecific) o;
    return Objects.equals(ecosystemSpecific, that.ecosystemSpecific);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ecosystemSpecific);
  }

  @Override
  public String toString() {
    return ECOSYSTEM_SPECIFIC_KEY + ": " + ecosystemSpecific;
  }
}
