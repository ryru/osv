package ch.addere.osv.impl.fields.affected;

import ch.addere.osv.fields.affected.EcosystemSpecific;
import java.util.Objects;

/**
 * Ecosystem Specific property.
 */
public final class EcosystemSpecificImpl implements EcosystemSpecific {

  public static final String ECOSYSTEM_SPECIFIC_KEY = "ecosystem_specific";

  private final String ecosystemSpecific;

  private EcosystemSpecificImpl(String ecosystemSpecific) {
    this.ecosystemSpecific = ecosystemSpecific;
  }

  public static EcosystemSpecificImpl of(String ecosystemSpecific) {
    return new EcosystemSpecificImpl(ecosystemSpecific);
  }

  @Override
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
    EcosystemSpecificImpl that = (EcosystemSpecificImpl) o;
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
