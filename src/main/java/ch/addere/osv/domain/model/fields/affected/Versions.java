package ch.addere.osv.domain.model.fields.affected;

import static java.lang.String.join;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Version identifies the affected version this vulnerability refers to.
 */
public final class Versions {

  public static final String VERSIONS_KEY = "versions";

  private final List<String> versions;

  private Versions(String... versions) {
    this.versions = new LinkedList<>();
    this.versions.addAll(List.of(versions));
  }

  /**
   * Create a versions object.
   *
   * @param versions version strings
   * @return valid versions
   */
  public static Versions of(String... versions) {
    return new Versions(versions);
  }

  public List<String> versions() {
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
    Versions that = (Versions) o;
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
