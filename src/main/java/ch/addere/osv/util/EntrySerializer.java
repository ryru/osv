package ch.addere.osv.util;

import static ch.addere.osv.property.AffectedValues.AFFECTED_KEY;
import static ch.addere.osv.property.AliasesValue.ALIASES_KEY;
import static ch.addere.osv.property.DetailsValue.DETAILS_KEY;
import static ch.addere.osv.property.IdValue.ID_KEY;
import static ch.addere.osv.property.ModifiedValue.MODIFIED_KEY;
import static ch.addere.osv.property.PublishedValue.PUBLISHED_KEY;
import static ch.addere.osv.property.ReferencesValues.REFERENCES_KEY;
import static ch.addere.osv.property.RelatedValue.RELATED_KEY;
import static ch.addere.osv.property.SchemaVersionValue.SCHEMA_VERSION_KEY;
import static ch.addere.osv.property.SummaryValue.SUMMARY_KEY;
import static ch.addere.osv.property.WithdrawnValue.WITHDRAWN_KEY;
import static ch.addere.osv.property.affected.DatabaseSpecificValue.DATABASE_SPECIFIC_KEY;
import static ch.addere.osv.property.affected.EcosystemSpecificValue.ECOSYSTEM_SPECIFIC_KEY;
import static ch.addere.osv.property.affected.PackageValues.PACKAGE_KEY;
import static ch.addere.osv.property.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.property.affected.VersionsValue.VERSIONS_KEY;
import static ch.addere.osv.property.affected.pckg.EcosystemValue.ECOSYSTEM_KEY;
import static ch.addere.osv.property.affected.pckg.NameValue.NAME_KEY;
import static ch.addere.osv.property.affected.pckg.PurlValue.PURL_KEY;
import static ch.addere.osv.property.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.property.affected.ranges.RangeTypeValue.TYPE_KEY;
import static ch.addere.osv.property.affected.ranges.RepoValue.REPO_KEY;
import static ch.addere.osv.property.references.ReferenceTypeValue.REFERENCE_TYPE_KEY;
import static ch.addere.osv.property.references.ReferenceUrlValue.REFERENCE_URL_KEY;

import ch.addere.osv.Entry;
import ch.addere.osv.Value;
import ch.addere.osv.property.AffectedValues;
import ch.addere.osv.property.DetailsValue;
import ch.addere.osv.property.IdValue;
import ch.addere.osv.property.ModifiedValue;
import ch.addere.osv.property.PublishedValue;
import ch.addere.osv.property.ReferencesValues;
import ch.addere.osv.property.SchemaVersionValue;
import ch.addere.osv.property.SummaryValue;
import ch.addere.osv.property.WithdrawnValue;
import ch.addere.osv.property.affected.DatabaseSpecificValue;
import ch.addere.osv.property.affected.EcosystemSpecificValue;
import ch.addere.osv.property.affected.PackageValues;
import ch.addere.osv.property.affected.Ranges;
import ch.addere.osv.property.affected.VersionsValue;
import ch.addere.osv.property.affected.pckg.EcosystemValue;
import ch.addere.osv.property.affected.pckg.NameValue;
import ch.addere.osv.property.affected.pckg.PurlValue;
import ch.addere.osv.property.affected.ranges.Event;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Serializer for open source vulnerabilities.
 */
public final class EntrySerializer extends StdSerializer<Entry> {

  EntrySerializer() {
    this(null);
  }

  private EntrySerializer(Class<Entry> t) {
    super(t);
  }

  private static void writeAllAffected(Entry value, JsonGenerator gen) throws IOException {
    gen.writeFieldName(AFFECTED_KEY);
    gen.writeStartArray();
    List<AffectedValues> affected = new ArrayList<>(value.affected());
    if (!affected.isEmpty()) {
      for (AffectedValues aff : affected) {
        writeAffected(gen, aff);
      }
    }
    gen.writeEndArray();
  }

  private static String writeId(IdValue id) {
    return id.value();
  }

  private static String writePublished(PublishedValue published) {
    return published.value().toString();
  }

  private static String writeModified(ModifiedValue modified) {
    return modified.value().toString();
  }

  private static String writeSchemaVersion(SchemaVersionValue schemaVersion) {
    return schemaVersion.value();
  }

  private static void writeAliases(Entry value, JsonGenerator gen) throws IOException {
    gen.writeFieldName(ALIASES_KEY);
    gen.writeStartArray();
    var ids = value.aliases().map(Value::value).orElse(List.of());
    for (var id : ids) {
      gen.writeString(writeId(id));
    }
    gen.writeEndArray();
  }

  private static void writeRelated(Entry value, JsonGenerator gen) throws IOException {
    gen.writeFieldName(RELATED_KEY);
    gen.writeStartArray();
    var ids = value.related().map(Value::value).orElse(List.of());
    for (var id : ids) {
      gen.writeString(writeId(id));
    }
    gen.writeEndArray();
  }

  private static String writeWithdrawn(WithdrawnValue withdrawn) {
    return withdrawn.value().toString();
  }

  private static String writeSummary(SummaryValue summary) {
    return summary.value();
  }

  private static String writeDetails(DetailsValue detailsValue) {
    return detailsValue.value().replace("\\n", "\n");
  }

  private static void writeAffected(JsonGenerator gen, AffectedValues aff) throws IOException {
    gen.writeStartObject();
    if (aff.pckg() != null) {
      gen.writeFieldName(PACKAGE_KEY);
      writePackage(aff.pckg(), gen);
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

  @Override
  public void serialize(Entry entry, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeStartObject();
    gen.writeStringField(ID_KEY, writeId(entry.id()));
    if (entry.published().isPresent()) {
      gen.writeStringField(PUBLISHED_KEY, writePublished(entry.published().get()));
    }
    gen.writeStringField(MODIFIED_KEY, writeModified(entry.modified()));
    if (entry.schemaVersion().isPresent()) {
      gen.writeStringField(SCHEMA_VERSION_KEY, writeSchemaVersion(entry.schemaVersion().get()));
    }
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
    if (entry.affected() != null && !entry.affected().isEmpty()) {
      writeAllAffected(entry, gen);
    }
    if (entry.references() != null && !entry.references().isEmpty()) {
      writeReferences(entry, gen);
    }
    gen.writeEndObject();
  }

  private static void writePackage(PackageValues pckg, JsonGenerator gen) throws IOException {
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

  private static String writeEcosystem(EcosystemValue ecosystem) {
    return ecosystem.value();
  }

  private static String writeName(NameValue name) {
    return name.value();
  }

  private static String writePurl(PurlValue purl) {
    return purl.value();
  }

  private static void writeRanges(Ranges ranges, JsonGenerator gen) throws IOException {
    gen.writeStartObject();
    gen.writeFieldName(TYPE_KEY);
    gen.writeString(ranges.type().value());
    if (ranges.repo().isPresent()) {
      gen.writeFieldName(REPO_KEY);
      gen.writeString(ranges.repo().get().value().toString());
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
    gen.writeFieldName(e.event().value());
    gen.writeString(e.release());
    gen.writeEndObject();
  }

  private static void writeVersions(VersionsValue versions, JsonGenerator gen) throws IOException {
    gen.writeStartArray();
    List<String> versionList = versions.value();
    for (String v : versionList) {
      gen.writeString(v);
    }
    gen.writeEndArray();
  }

  private static void writeEcosystemSpecific(EcosystemSpecificValue ecosystemSpecific,
      JsonGenerator gen)
      throws IOException {
    gen.writeRawValue(ecosystemSpecific.value());
  }

  private static void writeDatabaseSpecific(DatabaseSpecificValue databaseSpecificValue,
      JsonGenerator gen)
      throws IOException {
    gen.writeRawValue(databaseSpecificValue.value());
  }

  private static void writeReferences(Entry entry, JsonGenerator gen)
      throws IOException {
    gen.writeFieldName(REFERENCES_KEY);
    gen.writeStartArray();
    for (ReferencesValues r : entry.references()) {
      writeReference(r, gen);
    }
    gen.writeEndArray();
  }

  private static void writeReference(ReferencesValues reference, JsonGenerator gen)
      throws IOException {
    gen.writeStartObject();
    gen.writeFieldName(REFERENCE_TYPE_KEY);
    gen.writeString(reference.referenceType().value());
    gen.writeFieldName(REFERENCE_URL_KEY);
    gen.writeString(reference.referenceUrl().value().toString());
    gen.writeEndObject();
  }
}
