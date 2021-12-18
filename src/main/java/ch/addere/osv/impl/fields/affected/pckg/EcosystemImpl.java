package ch.addere.osv.impl.fields.affected.pckg;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import ch.addere.osv.fields.affected.pckg.Ecosystem;

/**
 * Ecosystems describes a concrete ecosystem this vulnerability applies to.
 */
public enum EcosystemImpl implements Ecosystem {

  CRATES_IO("crates.io"),
  DEBIAN("Debian"),
  GO("Go"),
  HEX("Hex"),
  LINUX("Linux"),
  MAVEN("Maven"),
  NPM("npm"),
  NU_GET("NuGet"),
  OSS_FUZZ("OSS-Fuzz"),
  PACKAGIST("Packagist"),
  PYPI("PyPI"),
  RUBY_GEMS("RubyGems");

  public static final String ECOSYSTEM_KEY = "ecosystem";

  private final String ecosystem;

  EcosystemImpl(String ecosystem) {
    this.ecosystem = ecosystem;
  }

  @Override
  public String value() {
    return this.ecosystem;
  }

  /**
   * Get Ecosystem from ecosystem string.
   *
   * @param ecosystem string value of an ecosystem
   * @return          valid Ecosystem
   */
  public static EcosystemImpl of(String ecosystem) {
    return stream(EcosystemImpl.values())
        .filter(values -> values.value().equals(ecosystem))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            format("'%s' is not a valid ecosystem", ecosystem)));
  }

  /**
   * Human-readable key-value string.
   *
   * @return key-value string
   */
  @Override
  public String toString() {
    return ECOSYSTEM_KEY + ": " + value();
  }
}
