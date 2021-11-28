package ch.addere.osv.impl.fields.affected.pckg;

import ch.addere.osv.fields.affected.pckg.Name;
import java.util.Objects;

/**
 * Name describes a library within an ecosystem.
 */
public final class NameImpl implements Name {

  public static final String NAME_KEY = "name";

  private final String name;

  private NameImpl(String name) {
    this.name = name;
  }

  public static NameImpl of(String name) {
    return new NameImpl(name);
  }

  @Override
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
    NameImpl that = (NameImpl) o;
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
