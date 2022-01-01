package ch.addere.osv;

import static java.lang.String.format;
import static java.util.Arrays.stream;

public enum EntrySchemaVersion {
  V_1_0_0("1.0.0"),
  V_1_1_0("1.1.0");

  private final String version;

  EntrySchemaVersion(String version) {
    this.version = version;
  }

  public String value() {
    return version;
  }

  public static EntrySchemaVersion latest() {
    return V_1_1_0;
  }

  public static EntrySchemaVersion fromVersionString(String version) {
    return stream(EntrySchemaVersion.values())
        .filter(values -> values.version.equals(version))
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException(format("'%s' is not a valid version", version)));
  }
}
