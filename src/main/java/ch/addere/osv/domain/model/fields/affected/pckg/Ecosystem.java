package ch.addere.osv.domain.model.fields.affected.pckg;

/**
 * Ecosystems describes a concrete ecosystem this vulnerability applies to.
 */
public enum Ecosystem {

  Go("Go"),
  NPM("npm"),
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

  @Override
  public String toString() {
    return ECOSYSTEM_KEY + ": " + ecosystem;
  }
}
