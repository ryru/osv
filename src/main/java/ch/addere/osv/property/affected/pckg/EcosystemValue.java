package ch.addere.osv.property.affected.pckg;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import ch.addere.osv.Value;

/**
 * Ecosystems describes a concrete ecosystem this vulnerability applies to.
 */
public enum EcosystemValue implements Value<String> {

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

  private final String value;

  EcosystemValue(String value) {
    this.value = value;
  }

  /**
   * Get Ecosystem from ecosystem string.
   *
   * @param ecosystem string value of an ecosystem
   * @return valid Ecosystem
   */
  public static EcosystemValue of(String ecosystem) {
    return stream(EcosystemValue.values())
        .filter(values -> values.value().equals(ecosystem))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            format("'%s' is not a valid ecosystem", ecosystem)));
  }

  @Override
  public String value() {
    return this.value;
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
