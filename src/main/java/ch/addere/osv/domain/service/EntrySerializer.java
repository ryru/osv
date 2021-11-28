package ch.addere.osv.domain.service;

import static ch.addere.osv.domain.model.fields.Affected.AFFECTED_KEY;
import static ch.addere.osv.domain.model.fields.Details.DETAILS_KEY;
import static ch.addere.osv.domain.model.fields.Id.ID_KEY;
import static ch.addere.osv.domain.model.fields.IdAggregate.ALIASES_KEY;
import static ch.addere.osv.domain.model.fields.IdAggregate.RELATED_KEY;
import static ch.addere.osv.domain.model.fields.Modified.MODIFIED_KEY;
import static ch.addere.osv.domain.model.fields.Published.PUBLISHED_KEY;
import static ch.addere.osv.domain.model.fields.References.REFERENCES_KEY;
import static ch.addere.osv.domain.model.fields.Summary.SUMMARY_KEY;
import static ch.addere.osv.domain.model.fields.Withdrawn.WITHDRAWN_KEY;
import static ch.addere.osv.domain.model.fields.affected.DatabaseSpecific.DATABASE_SPECIFIC_KEY;
import static ch.addere.osv.domain.model.fields.affected.EcosystemSpecific.ECOSYSTEM_SPECIFIC_KEY;
import static ch.addere.osv.domain.model.fields.affected.Package.PACKAGE_KEY;
import static ch.addere.osv.domain.model.fields.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.domain.model.fields.affected.Versions.VERSIONS_KEY;
import static ch.addere.osv.domain.model.fields.affected.pckg.Ecosystem.ECOSYSTEM_KEY;
import static ch.addere.osv.domain.model.fields.affected.pckg.Name.NAME_KEY;
import static ch.addere.osv.domain.model.fields.affected.pckg.Purl.PURL_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Repo.REPO_KEY;
import static ch.addere.osv.domain.model.fields.affected.ranges.Type.TYPE_KEY;
import static ch.addere.osv.domain.model.fields.reverences.ReferenceType.REFERENCE_TYPE_KEY;
import static ch.addere.osv.domain.model.fields.reverences.ReferenceUrl.REFERENCE_URL_KEY;

import ch.addere.osv.domain.model.Entry;
import ch.addere.osv.domain.model.fields.Affected;
import ch.addere.osv.domain.model.fields.Details;
import ch.addere.osv.domain.model.fields.Id;
import ch.addere.osv.domain.model.fields.IdAggregate;
import ch.addere.osv.domain.model.fields.Modified;
import ch.addere.osv.domain.model.fields.Published;
import ch.addere.osv.domain.model.fields.References;
import ch.addere.osv.domain.model.fields.Summary;
import ch.addere.osv.domain.model.fields.Withdrawn;
import ch.addere.osv.domain.model.fields.affected.DatabaseSpecific;
import ch.addere.osv.domain.model.fields.affected.EcosystemSpecific;
import ch.addere.osv.domain.model.fields.affected.Package;
import ch.addere.osv.domain.model.fields.affected.Ranges;
import ch.addere.osv.domain.model.fields.affected.Versions;
import ch.addere.osv.domain.model.fields.affected.pckg.Ecosystem;
import ch.addere.osv.domain.model.fields.affected.pckg.Name;
import ch.addere.osv.domain.model.fields.affected.pckg.Purl;
import ch.addere.osv.domain.model.fields.affected.ranges.Event;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Serializer for open source vulnerabilities.
 */
public class EntrySerializer extends StdSerializer<Entry> {

  protected EntrySerializer() {
    this(null);
  }

  protected EntrySerializer(Class<Entry> t) {
    super(t);
  }

  @Override
  public void serialize(Entry entry, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();
    gen.writeStringField(ID_KEY, writeId(entry.id()));
    if (entry.published().isPresent()) {
      gen.writeStringField(PUBLISHED_KEY, writePublished(entry.published().get()));
    }
    gen.writeStringField(MODIFIED_KEY, writeModified(entry.modified()));
    if (entry.aliases().isPresent()) {
      writeAliases(entry, gen);
    }
    if (entry.related().isPresent()) {
      writeRelated(entry, gen);
    }
    if (entry.withdrawn().isPresent()) {
      gen.writeStringField(WITHDRAWN_KEY, writeWithdrawn(entry.withdrawn().get()));
    }
    if (entry.summary().isPresent()) {
      gen.writeStringField(SUMMARY_KEY, writeSummary(entry.summary().get()));
    }
    if (entry.details().isPresent()) {
      gen.writeStringField(DETAILS_KEY, writeDetails(entry.details().get()));
    }
    if (!entry.affected().isEmpty()) {
      writeAffected(entry, gen);
    }
    if (!entry.references().isEmpty()) {
      writeReferences(entry, gen);
    }
    gen.writeEndObject();
  }

  private static String writeId(Id id) {
    return id.value();
  }

  private static String writePublished(Published published) {
    return published.value().toString();
  }

  private static String writeModified(Modified modified) {
    return modified.value().toString();
  }

  private static void writeAliases(Entry value, JsonGenerator gen) throws IOException {
    gen.writeFieldName(ALIASES_KEY);
    gen.writeStartArray();
    var ids = value.aliases().map(IdAggregate::ids).orElse(List.of());
    for (var id : ids) {
      gen.writeString(writeId(id));
    }
    gen.writeEndArray();
  }

  private static void writeRelated(Entry value, JsonGenerator gen) throws IOException {
    gen.writeFieldName(RELATED_KEY);
    gen.writeStartArray();
    var ids = value.related().map(IdAggregate::ids).orElse(List.of());
    for (var id : ids) {
      gen.writeString(writeId(id));
    }
    gen.writeEndArray();
  }

  private static String writeWithdrawn(Withdrawn withdrawn) {
    return withdrawn.value().toString();
  }

  private static String writeSummary(Summary summary) {
    return summary.value();
  }

  private static String writeDetails(Details details) {
    return details.value().replace("\\n", "\n");
  }

  private static void writeAffected(Entry value, JsonGenerator gen) throws IOException {
    gen.writeFieldName(AFFECTED_KEY);
    gen.writeStartArray();
    List<Affected> affected = new ArrayList<>(value.affected());
    if (!affected.isEmpty()) {
      for (Affected aff : affected) {
        gen.writeStartObject();
        if (aff.pckge() != null) {
          gen.writeFieldName(PACKAGE_KEY);
          writePackage(aff.pckge(), gen);
        }
        if (!aff.ranges().isEmpty()) {
          gen.writeFieldName(RANGES_KEY);
          gen.writeStartArray();
          List<Ranges> ranges = aff.ranges();
          for (Ranges range : ranges) {
            writeRanges(range, gen);
          }
          gen.writeEndArray();
        }
        if (aff.versions().isPresent()) {
          gen.writeFieldName(VERSIONS_KEY);
          writeVersions(aff.versions().get(), gen);
        }
        if (aff.ecosystemSpecific().isPresent()) {
          gen.writeFieldName(ECOSYSTEM_SPECIFIC_KEY);
          writeEcosystemSpecific(aff.ecosystemSpecific().get(), gen);
        }
        if (aff.databaseSpecific().isPresent()) {
          gen.writeFieldName(DATABASE_SPECIFIC_KEY);
          writeDatabaseSpecific(aff.databaseSpecific().get(), gen);
        }
        gen.writeEndObject();
      }
    }
    gen.writeEndArray();
  }

  private static void writePackage(Package pckg, JsonGenerator gen) throws IOException {
    gen.writeStartObject();
    gen.writeFieldName(ECOSYSTEM_KEY);
    gen.writeString(writeEcosystem(pckg.ecosystem()));
    gen.writeFieldName(NAME_KEY);
    gen.writeString(writeName(pckg.name()));
    if (pckg.purl().isPresent()) {
      gen.writeFieldName(PURL_KEY);
      gen.writeString(writePurl(pckg.purl().get()));
    }
    gen.writeEndObject();
  }

  private static String writeEcosystem(Ecosystem ecosystem) {
    return ecosystem.value();
  }

  private static String writeName(Name name) {
    return name.value();
  }

  private static String writePurl(Purl purl) {
    return purl.value();
  }

  private static void writeRanges(Ranges ranges, JsonGenerator gen) throws IOException {
    gen.writeStartObject();
    gen.writeFieldName(TYPE_KEY);
    gen.writeString(ranges.type().name());
    if (ranges.repo().isPresent()) {
      gen.writeFieldName(REPO_KEY);
      gen.writeString(ranges.repo().get().value());
    }
    if (!ranges.events().isEmpty()) {
      gen.writeFieldName(EVENTS_KEY);
      gen.writeStartArray();
      List<? extends Event> events = ranges.events();
      for (Event e : events) {
        writeRangeEvent(e, gen);
      }
      gen.writeEndArray();
    }
    gen.writeEndObject();
  }

  private static void writeRangeEvent(Event e, JsonGenerator gen) throws IOException {
    gen.writeStartObject();
    gen.writeFieldName(e.event().name());
    gen.writeString(e.release());
    gen.writeEndObject();
  }

  private static void writeVersions(Versions versions, JsonGenerator gen) throws IOException {
    gen.writeStartArray();
    List<String> versionList = versions.versions();
    for (String v : versionList) {
      gen.writeString(v);
    }
    gen.writeEndArray();
  }

  private static void writeEcosystemSpecific(EcosystemSpecific ecosystemSpecific, JsonGenerator gen)
      throws IOException {
    gen.writeRawValue(ecosystemSpecific.value());
  }

  private static void writeDatabaseSpecific(DatabaseSpecific databaseSpecific, JsonGenerator gen)
      throws IOException {
    gen.writeRawValue(databaseSpecific.value());
  }

  private static void writeReferences(Entry entry, JsonGenerator gen)
      throws IOException {
    gen.writeFieldName(REFERENCES_KEY);
    gen.writeStartArray();
    for (References r : entry.references()) {
      writeReference(r, gen);
    }
    gen.writeEndArray();
  }

  private static void writeReference(References reference, JsonGenerator gen) throws IOException {
    gen.writeStartObject();
    gen.writeFieldName(REFERENCE_TYPE_KEY);
    gen.writeString(reference.referenceType().name());
    gen.writeFieldName(REFERENCE_URL_KEY);
    gen.writeString(reference.referenceUrl().value());
    gen.writeEndObject();
  }
}
