package ch.addere.osv.domain.model.fields.affected;

import java.util.Objects;

/**
 * Database Specific property.
 */
public final class DatabaseSpecific {

  public static final String DATABASE_SPECIFIC_KEY = "database_specific";

  private final String databaseSpecific;

  private DatabaseSpecific(String databaseSpecific) {
    this.databaseSpecific = databaseSpecific;
  }

  public static DatabaseSpecific of(String databaseSpecific) {
    return new DatabaseSpecific(databaseSpecific);
  }

  public String value() {
    return databaseSpecific;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatabaseSpecific that = (DatabaseSpecific) o;
    return Objects.equals(databaseSpecific, that.databaseSpecific);
  }

  @Override
  public int hashCode() {
    return Objects.hash(databaseSpecific);
  }

  @Override
  public String toString() {
    return DATABASE_SPECIFIC_KEY + ": " + databaseSpecific;
  }
}
