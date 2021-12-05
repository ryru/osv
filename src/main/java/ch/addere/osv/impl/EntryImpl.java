package ch.addere.osv.impl;

import ch.addere.osv.Entry;
import ch.addere.osv.fields.Affected;
import ch.addere.osv.fields.Aliases;
import ch.addere.osv.fields.Details;
import ch.addere.osv.fields.Id;
import ch.addere.osv.fields.Modified;
import ch.addere.osv.fields.Published;
import ch.addere.osv.fields.References;
import ch.addere.osv.fields.Related;
import ch.addere.osv.fields.Summary;
import ch.addere.osv.fields.Withdrawn;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Entry is an open source vulnerability.
 */
public final class EntryImpl implements Entry {

  private final Id id;
  private final Modified modified;
  private Aliases aliases;
  private Related related;
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
  public Optional<Aliases> aliases() {
    return Optional.ofNullable(aliases);
  }

  @Override
  public Optional<Related> related() {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntryImpl entry = (EntryImpl) o;
    return id.equals(entry.id) && modified.equals(entry.modified) && Objects.equals(aliases,
        entry.aliases) && Objects.equals(related, entry.related)
        && Objects.equals(published, entry.published) && Objects.equals(withdrawn,
        entry.withdrawn) && Objects.equals(summary, entry.summary)
        && Objects.equals(details, entry.details) && Objects.equals(affected,
        entry.affected) && Objects.equals(references, entry.references);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, modified, aliases, related, published, withdrawn, summary, details,
        affected, references);
  }

  @Override
  public String toString() {
    return "EntryImpl{" +
        "id=" + id +
        ", modified=" + modified +
        ", aliases=" + aliases +
        ", related=" + related +
        ", published=" + published +
        ", withdrawn=" + withdrawn +
        ", summary=" + summary +
        ", details=" + details +
        ", affected=" + affected +
        ", references=" + references +
        '}';
  }

  /**
   * Builder for Entries.
   */
  public static class EntryBuilder {

    private final Id id;
    private final Modified modified;
    private Aliases aliases = null;
    private Related related = null;
    private Published published = null;
    private Withdrawn withdrawn = null;
    private Summary summary = null;
    private Details details = null;
    private List<Affected> affected = List.of();
    private List<References> references = List.of();

    public EntryBuilder(Id id, Modified modified) {
      this.id = id;
      this.modified = modified;
    }

    public EntryBuilder aliases(Aliases aliases) {
      this.aliases = aliases;
      return this;
    }

    public EntryBuilder related(Related related) {
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
