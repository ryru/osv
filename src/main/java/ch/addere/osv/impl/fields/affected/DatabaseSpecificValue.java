package ch.addere.osv.impl.fields.affected;

import ch.addere.osv.fields.Value;
import java.util.Objects;

/**
 * Database Specific property.
 */
public final class DatabaseSpecificValue implements Value<String> {

  public static final String DATABASE_SPECIFIC_KEY = "database_specific";

  private final String value;

  private DatabaseSpecificValue(String value) {
    Objects.requireNonNull(value, "argument database specific must not be null");
    this.value = value;
  }

  public static DatabaseSpecificValue fromString(String databaseSpecific) {
    return new DatabaseSpecificValue(databaseSpecific);
  }

  @Override
  public String value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatabaseSpecificValue that = (DatabaseSpecificValue) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return DATABASE_SPECIFIC_KEY + ": " + value;
  }
}
