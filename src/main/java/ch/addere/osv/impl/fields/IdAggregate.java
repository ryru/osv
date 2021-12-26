package ch.addere.osv.impl.fields;

import ch.addere.osv.fields.Value;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Aggregate of IDs representing other vulnerability entries.
 */
public final class IdAggregate implements Value<List<Id>> {

  public static final String ALIASES_KEY = "aliases";
  public static final String RELATED_KEY = "related";

  private final List<Id> ids;

  private IdAggregate(Id... ids) {
    Objects.requireNonNull(ids, "argument ids must not be null");
    this.ids = List.of(ids);
    if (ids.length == 0) {
      throw new IllegalArgumentException("no ID, at least one ID must be provided");
    }
  }

  /**
   * Each ID represents a vulnerability database entry.
   *
   * @param ids to be added to this aggregate
   * @return a new aggregate with all ids
   */
  public static IdAggregate of(Id... ids) {
    return new IdAggregate(ids);
  }

  /**
   * Get a copy of all IDs.
   *
   * @return List of IDs
   */
  @Override
  public List<Id> value() {
    return List.copyOf(ids);
  }

  /**
   * Add ID, if it is not already included.
   * <p>IDs can not be duplicated within this aggregate.</p>
   *
   * @param id which will be added if not already included
   * @return a copy of the original aggregate, if ID is newly added
   */
  public IdAggregate add(Id id) {
    if (contains(id)) {
      return this;
    } else {
      List<Id> tmpIds = new LinkedList<>(ids);
      tmpIds.add(id);
      return of(tmpIds.toArray(new Id[0]));
    }
  }

  /**
   * Remove ID, if it is included, otherwise return unmodified.
   *
   * @param id which will be removed if found
   * @return a copy of the original aggregate, if found
   */
  public IdAggregate remove(Id id) {
    if (contains(id)) {
      List<Id> tmpIds = new LinkedList<>(ids);
      tmpIds.remove(id);
      return of(tmpIds.toArray(new Id[0]));
    } else {
      return this;
    }
  }

  /**
   * Check if ID is part of this aggregate.
   *
   * @param id which is looked up
   * @return ture if ID is included
   */
  public boolean contains(Id id) {
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
    IdAggregate that = (IdAggregate) o;
    return Objects.equals(ids, that.ids);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ids);
  }
}
