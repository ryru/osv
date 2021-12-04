package ch.addere.osv.impl.fields.affected;

import static java.lang.String.join;

import ch.addere.osv.fields.affected.Versions;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Version identifies the affected version this vulnerability refers to.
 */
public final class VersionsImpl implements Versions {

  public static final String VERSIONS_KEY = "versions";

  private final List<String> versions;

  private VersionsImpl(String... versions) {
    Objects.requireNonNull(versions, "argument versions must not be null");
    this.versions = new LinkedList<>(List.of(versions));
  }

  /**
   * Create a versions object.
   *
   * @param versions version strings
   * @return valid versions
   */
  public static VersionsImpl of(String... versions) {
    return new VersionsImpl(versions);
  }

  @Override
  public List<String> value() {
    return new LinkedList<>(versions);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VersionsImpl that = (VersionsImpl) o;
    return Objects.equals(versions, that.versions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(versions);
  }

  @Override
  public String toString() {
    return VERSIONS_KEY + ": " + join(", ", versions);
  }
}
