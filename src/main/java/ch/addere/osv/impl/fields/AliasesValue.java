package ch.addere.osv.impl.fields;

import static java.util.stream.Collectors.joining;

import ch.addere.osv.fields.Value;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Aliases property.
 */
public class AliasesValue implements Value<List<IdValue>> {

  public static final String ALIASES_KEY = "aliases";

  private final List<IdValue> ids;

  private AliasesValue(List<IdValue> ids) {
    if (ids.isEmpty()) {
      throw new IllegalArgumentException("no ID, at least one ID must be provided");
    }
    this.ids = ids;
  }

  /**
   * Each ID represents a vulnerability database entry.
   *
   * @param ids to be added
   * @return a new AliasesValue with all ids
   */
  public static AliasesValue of(IdValue... ids) {
    Objects.requireNonNull(ids, "argument ids must not be null");
    return new AliasesValue(List.of(ids));
  }

  @Override
  public List<IdValue> value() {
    return List.copyOf(ids);
  }

  /**
   * Add ID, if it is not already included.
   * <p>IDs can not be duplicated within this AliasesValue.</p>
   *
   * @param id which will be added if not already included
   * @return a copy of the original AliasesValue, if ID is newly added
   */
  public AliasesValue add(IdValue id) {
    if (contains(id)) {
      return this;
    } else {
      List<IdValue> tmpIds = new LinkedList<>(ids);
      tmpIds.add(id);
      return of(tmpIds.toArray(new IdValue[0]));
    }
  }

  /**
   * Remove ID, if it is included, otherwise return unmodified.
   *
   * @param id which will be removed if found
   * @return a copy of the original AliasesValue, if found
   */
  public AliasesValue remove(IdValue id) {
    if (contains(id)) {
      List<IdValue> tmpIds = new LinkedList<>(ids);
      tmpIds.remove(id);
      return of(tmpIds.toArray(new IdValue[0]));
    } else {
      return this;
    }
  }

  /**
   * Check if ID is part of this AliasesValue.
   *
   * @param id which is looked up
   * @return ture if ID is included
   */
  public boolean contains(IdValue id) {
    return ids.contains(id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AliasesValue that = (AliasesValue) o;
    return Objects.equals(ids, that.ids);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ids);
  }

  @Override
  public String toString() {
    return ALIASES_KEY + ": " + ids.stream()
        .map(IdValue::value)
        .collect(joining(", "));
  }
}
