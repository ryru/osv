package ch.addere.osvs.lib.field.types;

/** Specified references for {@link ch.addere.osvs.lib.field.ReferencesTypeField}. */
public enum Reference {

  /** A published security advisory for the vulnerability. */
  ADVISORY("ADVISORY"),

  /** An article or blog post describing the vulnerability. */
  ARTICLE("ARTICLE"),

  /** A source code browser link to the fix. */
  FIX("FIX"),

  /** A home web page for the package. */
  PACKAGE("PACKAGE"),

  /** A report, typically on a bug or issue tracker, of the vulnerability. */
  REPORT("REPORT"),

  /** A web page of some unspecified kind. */
  WEB("WEB");

  private final String type;

  Reference(String type) {
    this.type = type;
  }
}
