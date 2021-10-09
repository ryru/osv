package ch.addere.osv.domain.model.fields.affected.pckg;

import java.util.Objects;

/**
 * Name describes a library within an ecosystem.
 */
public final class Name {

  public static final String NAME_KEY = "name";

  private final String name;

  private Name(String name) {
    this.name = name;
  }

  public static Name of(String name) {
    return new Name(name);
  }

  public String value() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Name that = (Name) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return NAME_KEY + ": " + name;
  }
}
