package ch.addere.osv.impl.fields.affected.ranges;

/**
 * Range types that are supported.
 */
public enum Type {
  SEMVER("SEMVER"),
  GIT("GIT"),
  ECOSYSTEM("ECOSYSTEM");

  public static final String TYPE_KEY = "type";

  private final String type;

  Type(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return TYPE_KEY + ": " + type;
  }
}
