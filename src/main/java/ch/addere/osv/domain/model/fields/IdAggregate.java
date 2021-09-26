package ch.addere.osv.domain.model.fields;

import java.util.LinkedList;
import java.util.List;

/**
 * Aggregate of IDs representing other vulnerability entries.
 */
public final class IdAggregate {

  public static final String ALIASES_KEY = "aliases";
  public static final String RELATED_KEY = "related";

  private final List<Id> related;

  private IdAggregate(List<Id> related) {
    this.related = related;
  }

  /**
   * Each ID represents a vulnerability database entry.
   *
   * @param ids to be added to this aggregate
   * @return a new aggregate with all ids
   */
  public static IdAggregate of(Id... ids) {
    if (ids.length == 0) {
      throw new IllegalArgumentException("no ID, at least one ID must be provided");
    }
    return new IdAggregate(List.of(ids));
  }

  /**
   * Get a copy of all IDs.
   *
   * @return IDs
   */
  public List<Id> ids() {
    return List.copyOf(related);
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
      List<Id> tmpIds = new LinkedList<>(related);
      tmpIds.add(id);
      return IdAggregate.of(tmpIds.toArray(new Id[0]));
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
      List<Id> tmpIds = new LinkedList<>(related);
      tmpIds.remove(id);
      return IdAggregate.of(tmpIds.toArray(new Id[0]));
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
    return related.contains(id);
  }
}
