package ch.addere.osvs.lib.impl;

import ch.addere.osvs.lib.field.ReferencesTypeField;
import ch.addere.osvs.lib.field.ReferencesUrlField;
import ch.addere.osvs.lib.field.types.ReferencesEntry;

import java.util.Objects;

/** {@link ch.addere.osvs.lib.field.types.ReferencesEntry} implementation. */
public final class ReferencesEntryImpl implements ReferencesEntry {

  private final ReferencesTypeField typeField;
  private final ReferencesUrlField urlField;

  private ReferencesEntryImpl(ReferencesTypeField typeField, ReferencesUrlField urlField) {
    Objects.requireNonNull(typeField, "argument typeField must not be null");
    Objects.requireNonNull(urlField, "argument urlField must not be null");

    this.typeField = typeField;
    this.urlField = urlField;
  }

  /** Create a new instance. */
  public static ReferencesEntry of(ReferencesTypeField typeField, ReferencesUrlField urlField) {
    return new ReferencesEntryImpl(typeField, urlField);
  }

  @Override
  public ReferencesTypeField getType() {
    return typeField;
  }

  @Override
  public ReferencesUrlField getUrl() {
    return urlField;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ReferencesEntryImpl that = (ReferencesEntryImpl) o;

    if (!typeField.equals(that.typeField)) return false;
    return urlField.equals(that.urlField);
  }

  @Override
  public int hashCode() {
    int result = typeField.hashCode();
    result = 31 * result + urlField.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "(" + typeField + ", " + urlField + ")";
  }
}
