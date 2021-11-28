package ch.addere.osv.impl.fields.affected;

import ch.addere.osv.fields.affected.DatabaseSpecific;
import java.util.Objects;

/**
 * Database Specific property.
 */
public final class DatabaseSpecificImpl implements DatabaseSpecific {

  public static final String DATABASE_SPECIFIC_KEY = "database_specific";

  private final String databaseSpecific;

  private DatabaseSpecificImpl(String databaseSpecific) {
    this.databaseSpecific = databaseSpecific;
  }

  public static DatabaseSpecificImpl of(String databaseSpecific) {
    return new DatabaseSpecificImpl(databaseSpecific);
  }

  @Override
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
    DatabaseSpecificImpl that = (DatabaseSpecificImpl) o;
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
