package ch.addere.osv.impl;

import static java.util.stream.Collectors.joining;

import ch.addere.osv.Entry;
import ch.addere.osv.fields.Affected;
import ch.addere.osv.fields.Aliases;
import ch.addere.osv.fields.Id;
import ch.addere.osv.fields.References;
import ch.addere.osv.fields.Related;
import ch.addere.osv.impl.fields.DetailsValue;
import ch.addere.osv.impl.fields.ModifiedValue;
import ch.addere.osv.impl.fields.PublishedValue;
import ch.addere.osv.impl.fields.SummaryValue;
import ch.addere.osv.impl.fields.WithdrawnValue;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Entry is an open source vulnerability.
 */
public final class EntryImpl implements Entry {

  private final Id id;
  private final ModifiedValue modified;
  private Aliases aliases;
  private Related related;
  private PublishedValue published;
  private WithdrawnValue withdrawn;
  private SummaryValue summary;
  private DetailsValue detailsValue;
  private List<Affected> affected;
  private List<References> references;

  private EntryImpl(Id id, ModifiedValue modified) {
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
  public static EntryBuilder builder(Id id, ModifiedValue modified) {
    return new EntryBuilder(id, modified);
  }

  @Override
  public Id id() {
    return id;
  }

  @Override
  public ModifiedValue modified() {
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
  public Optional<PublishedValue> published() {
    return Optional.ofNullable(published);
  }

  @Override
  public Optional<WithdrawnValue> withdrawn() {
    return Optional.ofNullable(withdrawn);
  }

  @Override
  public Optional<SummaryValue> summary() {
    return Optional.ofNullable(summary);
  }

  @Override
  public Optional<DetailsValue> details() {
    return Optional.ofNullable(detailsValue);
  }

  @Override
  public List<Affected> affected() {
    if (affected != null) {
      return new LinkedList<>(affected);
    } else {
      return List.of();
    }
  }

  @Override
  public List<References> references() {
    if (references != null) {
      return new LinkedList<>(references);
    } else {
      return List.of();
    }
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
        && Objects.equals(detailsValue, entry.detailsValue) && Objects.equals(affected,
        entry.affected) && Objects.equals(references, entry.references);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, modified, aliases, related, published, withdrawn, summary, detailsValue,
        affected, references);
  }

  @Override
  public String toString() {
    return Stream.of(
            id,
            modified,
            aliases,
            related,
            published,
            withdrawn,
            summary,
            detailsValue,
            affected,
            references
        )
        .filter(Objects::nonNull)
        .map(Objects::toString)
        .filter(s -> !s.isEmpty())
        .collect(joining(", "));
  }

  /**
   * Builder for Entries.
   */
  public static class EntryBuilder {

    private final Id id;
    private final ModifiedValue modified;
    private Aliases aliases = null;
    private Related related = null;
    private PublishedValue published = null;
    private WithdrawnValue withdrawn = null;
    private SummaryValue summary = null;
    private DetailsValue detailsValue = null;
    private List<Affected> affected = null;
    private List<References> references = null;

    /**
     * Entry builder.
     *
     * @param id       ID of this entry
     * @param modified Modified of this entry
     */
    public EntryBuilder(Id id, ModifiedValue modified) {
      Objects.requireNonNull(id, "argument id must not be null");
      Objects.requireNonNull(modified, "argument modified must not be null");
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

    public EntryBuilder published(PublishedValue published) {
      this.published = published;
      return this;
    }

    public EntryBuilder withdrawn(WithdrawnValue withdrawn) {
      this.withdrawn = withdrawn;
      return this;
    }

    public EntryBuilder summary(SummaryValue summary) {
      this.summary = summary;
      return this;
    }

    public EntryBuilder details(DetailsValue detailsValue) {
      this.detailsValue = detailsValue;
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
      entry.detailsValue = detailsValue;
      entry.affected = affected;
      entry.references = references;
      return entry;
    }
  }
}
