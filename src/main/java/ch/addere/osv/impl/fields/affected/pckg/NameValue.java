package ch.addere.osv.impl.fields.affected.pckg;

import ch.addere.osv.fields.Value;
import java.util.Objects;

/**
 * Name describes a library within an ecosystem.
 */
public final class NameValue implements Value<String> {

  public static final String NAME_KEY = "name";

  private final String value;

  private NameValue(String value) {
    Objects.requireNonNull(value, "argument name must not be null");
    this.value = value;
  }

  public static NameValue fromString(String name) {
    return new NameValue(name);
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
    NameValue that = (NameValue) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return NAME_KEY + ": " + value;
  }
}
