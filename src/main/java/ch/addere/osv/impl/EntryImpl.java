package ch.addere.osv.impl;

import ch.addere.osv.Entry;
import ch.addere.osv.fields.Affected;
import ch.addere.osv.fields.Details;
import ch.addere.osv.fields.Id;
import ch.addere.osv.fields.Modified;
import ch.addere.osv.fields.Published;
import ch.addere.osv.fields.References;
import ch.addere.osv.fields.Summary;
import ch.addere.osv.fields.Withdrawn;
import ch.addere.osv.impl.fields.IdAggregate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Entry is an open source vulnerability.
 */
public final class EntryImpl implements Entry {

  private final Id id;
  private final Modified modified;
  private IdAggregate aliases;
  private IdAggregate related;
  private Published published;
  private Withdrawn withdrawn;
  private Summary summary;
  private Details details;
  private List<Affected> affected;
  private List<References> references;

  private EntryImpl(Id id, Modified modified) {
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

  @Override
  public Id id() {
    return id;
  }

  @Override
  public Modified modified() {
    return modified;
  }

  @Override
  public Optional<IdAggregate> aliases() {
    return Optional.ofNullable(aliases);
  }

  @Override
  public Optional<IdAggregate> related() {
    return Optional.ofNullable(related);
  }

  @Override
  public Optional<Published> published() {
    return Optional.ofNullable(published);
  }

  @Override
  public Optional<Withdrawn> withdrawn() {
    return Optional.ofNullable(withdrawn);
  }

  @Override
  public Optional<Summary> summary() {
    return Optional.ofNullable(summary);
  }

  @Override
  public Optional<Details> details() {
    return Optional.ofNullable(details);
  }

  @Override
  public Set<Affected> affected() {
    return new HashSet<>(affected);
  }

  @Override
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
    private List<Affected> affected = List.of();
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
      this.affected = List.of(affected);
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
    public EntryImpl build() {
      EntryImpl entry = new EntryImpl(id, modified);
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
