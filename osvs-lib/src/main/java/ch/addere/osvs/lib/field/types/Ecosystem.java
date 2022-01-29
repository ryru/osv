package ch.addere.osvs.lib.field.types;

/** The ecosystem identifies the overall library ecosystem. */
public enum Ecosystem {

  /** The crates.io ecosystem for Rust; the {@code name} field is a crate name. */
  CRATES_IO("crates.io"),

  /** The Debian package ecosystem; the {@code name} is the name of the package. */
  DEBIAN("Debian"),

  /** The Go ecosystem. the {@code name} field is a Go module path. */
  GO("Go"),

  /** The package manager for the Erlang ecosystem; the {@code name} is a Hex package name. */
  HEX("Hex"),

  /** The Linux kernel. The only supported {@code name} is {@code Kernel}. */
  LINUX("Linux"),

  /** The Maven Java package ecosystem. The {@code name} field is a Maven package name. */
  MAVEN("Maven"),

  /** The NPM ecosystem; the {@code name} field is an NPM package name. */
  NPM("npm"),

  /** The NuGet package ecosystem. The {@code name} field is a NuGet package name. */
  NU_GET("NuGet"),

  /**
   * For reports from the OSS-Fuzz project that have no more appropriate ecosystem; the name field
   * is the {@code name} assigned by the OSS-Fuzz project, as recorded in the submitted fuzzing
   * configuration.
   */
  OSS_FUZZ("OSS-Fuzz"),

  /** The PHP package manager ecosystem; the {@code name} is a package name. */
  PACKAGIST("Packagist"),

  /** The Python PyPI ecosystem; the {@code name} field is a normalized PyPI package name. */
  PYPI("PyPI"),

  /** The RubyGems ecosystem; the {@code name} field is a gem name. */
  RUBY_GEMS("RubyGems");

  private final String type;

  Ecosystem(String type) {
    this.type = type;
  }
}
