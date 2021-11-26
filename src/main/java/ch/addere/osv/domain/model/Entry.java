package ch.addere.osv.domain.model;

import ch.addere.osv.domain.model.fields.Affected;
import ch.addere.osv.domain.model.fields.Details;
import ch.addere.osv.domain.model.fields.Id;
import ch.addere.osv.domain.model.fields.IdAggregate;
import ch.addere.osv.domain.model.fields.Modified;
import ch.addere.osv.domain.model.fields.Published;
import ch.addere.osv.domain.model.fields.References;
import ch.addere.osv.domain.model.fields.Summary;
import ch.addere.osv.domain.model.fields.Withdrawn;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Entry is an open source vulnerability.
 */
public final class Entry {

  private final Id id;
  private final Modified modified;
  private IdAggregate aliases;
  private IdAggregate related;
  private Published published;
  private Withdrawn withdrawn;
  private Summary summary;
  private Details details;
  private Set<Affected> affected;
  private List<References> references;

  private Entry(Id id, Modified modified) {
    this.id = id;
    this.modified = modified;
  }

  /**
   * Entity builder creates new entities.
   * <p>A minimal entity requires an ID and a modified attribute.</p>
   *
   * @param id       unique ID
   * @param modified last modification date
   * @return valid entity
   */
  public static EntryBuilder builder(Id id, Modified modified) {
    return new EntryBuilder(id, modified);
  }

  public Id id() {
    return id;
  }

  public Modified modified() {
    return modified;
  }

  public Optional<IdAggregate> aliases() {
    return Optional.ofNullable(aliases);
  }

  public Optional<IdAggregate> related() {
    return Optional.ofNullable(related);
  }

  public Optional<Published> published() {
    return Optional.ofNullable(published);
  }

  public Optional<Withdrawn> withdrawn() {
    return Optional.ofNullable(withdrawn);
  }

  public Optional<Summary> summary() {
    return Optional.ofNullable(summary);
  }

  public Optional<Details> details() {
    return Optional.ofNullable(details);
  }

  public Set<Affected> affected() {
    return new HashSet<>(affected);
  }

  public List<References> references() {
    return new LinkedList<>(references);
  }

  /**
   * Builder for Entries.
   */
  public static class EntryBuilder {

    private final Id id;
    private final Modified modified;
    private IdAggregate aliases = null;
    private IdAggregate related = null;
    private Published published = null;
    private Withdrawn withdrawn = null;
    private Summary summary = null;
    private Details details = null;
    private Set<Affected> affected = Set.of();
    private List<References> references = null;

    public EntryBuilder(Id id, Modified modified) {
      this.id = id;
      this.modified = modified;
    }

    public EntryBuilder aliases(IdAggregate aliases) {
      this.aliases = aliases;
      return this;
    }

    public EntryBuilder related(IdAggregate related) {
      this.related = related;
      return this;
    }

    public EntryBuilder published(Published published) {
      this.published = published;
      return this;
    }

    public EntryBuilder withdrawn(Withdrawn withdrawn) {
      this.withdrawn = withdrawn;
      return this;
    }

    public EntryBuilder summary(Summary summary) {
      this.summary = summary;
      return this;
    }

    public EntryBuilder details(Details details) {
      this.details = details;
      return this;
    }

    public EntryBuilder affected(Affected... affected) {
      this.affected = Set.of(affected);
      return this;
    }

    public EntryBuilder references(References... references) {
      this.references = List.of(references);
      return this;
    }

    /**
     * Build a concrete entry.
     *
     * @return valid entry
     */
    public Entry build() {
      Entry entry = new Entry(id, modified);
      entry.aliases = aliases;
      entry.related = related;
      entry.published = published;
      entry.withdrawn = withdrawn;
      entry.summary = summary;
      entry.details = details;
      entry.affected = affected;
      entry.references = references;
      return entry;
    }
  }
}
