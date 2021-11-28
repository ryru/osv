package ch.addere.osv.domain.model.fields.affected.pckg;

import static java.lang.String.format;
import static java.util.Arrays.stream;

/**
 * Ecosystems describes a concrete ecosystem this vulnerability applies to.
 */
public enum Ecosystem {

  Go("Go"),
  npm("npm"),
  OSS_FUZZ("OSS-Fuzz"),
  PYPI("PyPI"),
  RUBY_GEMS("RubyGems"),
  CRATES_IO("crates.io"),
  PACKAGIST("packagist"),
  MAVEN("Maven"),
  NU_GET("NuGet"),
  LINUX("Linux");

  public static final String ECOSYSTEM_KEY = "ecosystem";

  private final String ecosystem;

  Ecosystem(String ecosystem) {
    this.ecosystem = ecosystem;
  }

  public String value() {
    return ecosystem;
  }

  /**
   * Get Ecosystem from ecosystem string.
   *
   * @param ecosystem string value of an ecosystem
   * @return valid Ecosystem
   */
  public static Ecosystem value(String ecosystem) {
    return stream(Ecosystem.values())
        .filter(env -> env.ecosystem.equals(ecosystem))
        .findFirst().orElseThrow(() -> new IllegalArgumentException(
            format("'%s' is not a valid ecosystem", ecosystem)));
  }

  @Override
  public String toString() {
    return ECOSYSTEM_KEY + ": " + ecosystem;
  }
}
