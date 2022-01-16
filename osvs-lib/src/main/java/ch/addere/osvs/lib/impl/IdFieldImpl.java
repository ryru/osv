package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.IdField;
import ch.addere.osvs.lib.field.types.IdDb;
import ch.addere.osvs.lib.validation.RuleResult;
import ch.addere.osvs.lib.validation.Validation;

import java.util.Objects;

/** {@link ch.addere.osvs.lib.field.IdField} implementation. */
public final class IdFieldImpl implements IdField {

  private static final String JSON_KEY = "id";

  private final IdDb db;
  private final String entryId;

  private IdFieldImpl(IdDb db, String entryId) {
    Objects.requireNonNull(db, "argument db must not be null");
    Objects.requireNonNull(entryId, "argument entryId must not be null");

    this.db = db;
    this.entryId = entryId;

    Validation<IdFieldImpl> validator = new IdFieldValidation(this);
    RuleResult result = validator.validate();
    if (!result.isValid()) {
      throw new IllegalArgumentException(result.getViolationMsg());
    }
  }

  /** Create a new instance. */
  public static IdField of(IdDb db, String entryId) {
    return new IdFieldImpl(db, entryId);
  }

  @Override
  public String getKey() {
    return JSON_KEY;
  }

  @Override
  public String getValue() {
    return db + "-" + entryId;
  }

  @Override
  public IdDb getDb() {
    return db;
  }

  @Override
  public String getEntryId() {
    return entryId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    IdFieldImpl idField = (IdFieldImpl) o;

    if (db != idField.db) {
      return false;
    }
    return entryId.equals(idField.entryId);
  }

  @Override
  public int hashCode() {
    int result = db.hashCode();
    result = 31 * result + entryId.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return JSON_KEY + ": " + getValue();
  }
}
