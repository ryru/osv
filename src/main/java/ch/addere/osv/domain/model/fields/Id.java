package ch.addere.osv.domain.model.fields;

import static ch.addere.osv.domain.model.fields.Id.Database.valueOf;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import lombok.NonNull;
import lombok.Value;

/**
 * ID is a unique identifier for a vulnerability entry.
 */
@Value
public class Id implements Jsonable {

  Database db;
  String entryId;

  @Override
  public String toJson() {
    return db.name() + "-" + entryId;
  }

  /**
   * Create an ID object from an ID string.
   *
   * @param identifier ID consists of the vulnerability database and an entry ID
   * @return an instance of a parsed ID
   */
  public static Id create(@NonNull String identifier) {
    var tokenized = identifier.split("-");
    final var dbName = tokenized[0];
    Database database = valueOf(dbName);
    var entryId = stream(tokenized)
        .skip(1)
        .collect(joining("-"));

    return new Id(database, entryId);
  }

  /**
   * Vulnerability databases supported by the specification.
   */
  public enum Database {
    GO("GO"),
    OSV("OSV"),
    PYSEC("PYSEC"),
    RUSTSEC("RUSTSEC"),
    UVI("UVI");

    private final String database;

    Database(String databaseName) {
      database = databaseName;
    }

    public String toString() {
      return database;
    }
  }
}
