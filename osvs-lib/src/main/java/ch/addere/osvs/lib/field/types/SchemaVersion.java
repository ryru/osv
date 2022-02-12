package ch.addere.osvs.lib.field.types;

/** Specifies the version of the OSV schema. */
public enum SchemaVersion {

  /** Version 1.0.0, released on September 8, 2021 */
  V_1_0_0("1.0.0"),

  /** Version 1.1.0, released on December 15, 2021 */
  V_1_1_0("1.1.0"),

  /** Version 1.2.0, released on January 24, 2022 */
  V_1_2_0("1.2.0");

  private final String version;

  SchemaVersion(String version) {
    this.version = version;
  }

  /**
   * The latest supported version of the OSV schema.
   *
   * @return most recent OSV schema version
   */
  public static SchemaVersion latest() {
    return V_1_2_0;
  }

  @Override
  public String toString() {
    return version;
  }
}
