package ch.addere.osvs.lib.field.types;

/** Specified databases for {@link ch.addere.osvs.lib.field.IdField}. */
public enum IdDb {

  /** The Go vulnerability database. */
  GO("GO"),

  /** The GSD database. */
  GSD("GSD"),

  /** The OSV vulnerability database. */
  OSV("OSV"),

  /** The PyPI vulnerability database. */
  PYSEC("PYSEC"),

  /** The Rust crates vulnerability database. */
  RUSTSEC("RUSTSEC");

  private final String db;

  IdDb(String db) {
    this.db = db;
  }
}
