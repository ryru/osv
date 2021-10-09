package ch.addere.osv.domain.service.serializer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.domain.model.fields.Affected;
import ch.addere.osv.domain.model.fields.Affected.AffectedBuilder;
import ch.addere.osv.domain.model.fields.affected.Package;
import ch.addere.osv.domain.model.fields.affected.Ranges;
import ch.addere.osv.domain.model.fields.affected.Versions;
import ch.addere.osv.domain.model.fields.affected.pckg.Ecosystem;
import ch.addere.osv.domain.model.fields.affected.pckg.Name;
import ch.addere.osv.domain.model.fields.affected.pckg.Purl;
import ch.addere.osv.domain.model.fields.affected.ranges.TypeSemVer;
import ch.addere.osv.domain.model.fields.affected.ranges.events.EventSpecifier;
import ch.addere.osv.domain.model.fields.affected.ranges.events.SemVerEvent;
import ch.addere.osv.domain.service.OsvParserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AffectedDeserializerTest {

  private static final ObjectMapper mapper = new ObjectMapper();
  private static final String PACKAGE =
      "    \"package\": {"
          + "  \"ecosystem\": \"Go\","
          + "  \"name\": \"crypto/elliptic\""
          + "}";
  private static final String PACKAGE_WITH_PURL =
      "    \"package\": {"
          + "  \"ecosystem\": \"Go\","
          + "  \"name\": \"crypto/elliptic\","
          + "  \"purl\": \"https://purl.url\""
          + "}";
  private static final String RANGE =
      "    \"ranges\": ["
          + "  {"
          + "    \"type\": \"SEMVER\","
          + "    \"events\": ["
          + "      {\"introduced\": \"1.0.0\"},"
          + "      {\"fixed\": \"1.14.14\"},"
          + "      {\"introduced\": \"1.15.0\"},"
          + "      {\"fixed\": \"1.15.17\"}"
          + "    ]"
          + "  }"
          + "]";
  private static final String VERSION = "\"versions\": ["
      + "\"2.8.0\", \"2.8.0.post1\", \"2.8.0.post2\", \"2.9.0\", \"2.9.1\", \"2.9.2\"]";
  private static final String AFFECTED = "[" + "{" + PACKAGE + "," + RANGE + "}" + "]";
  private static final String AFFECTED_WITH_PURL =
      "[" + "{" + PACKAGE_WITH_PURL + "," + RANGE + "}" + "]";
  private static final String AFFECTED_WITH_VERSION =
      "[" + "{" + PACKAGE + "," + VERSION + "}" + "]";

  @Test
  void testInvalidJsonInput() {
    assertThatThrownBy(() -> deserialize(""))
        .isInstanceOf(OsvParserException.class)
        .hasMessageContaining("affected node is not an array node");
  }

  @Test
  void testValidAffected() throws JsonProcessingException, OsvParserException {
    Affected affected = affected(null);
    Set<Affected> deserializedAffected = deserialize(AFFECTED);
    assertThat(deserializedAffected).containsExactly(affected);
  }

  @Test
  void testValidAffectedWithPurl() throws JsonProcessingException, OsvParserException {
    Affected affected = affected(Purl.of("https://purl.url"));
    Set<Affected> deserializedAffected = deserialize(AFFECTED_WITH_PURL);
    assertThat(deserializedAffected).containsExactly(affected);
  }

  @Test
  void testValidAffectedWithVerson() throws JsonProcessingException, OsvParserException {
    Affected affected = affectedWithVersion();
    Set<Affected> deserializedAffected = deserialize(AFFECTED_WITH_VERSION);
    assertThat(deserializedAffected).containsExactly(affected);
  }

  private static Affected affected(Purl purl) {
    Package pckg = Package.of(Ecosystem.Go, Name.of("crypto/elliptic"), purl);
    SemVerEvent introduced1 = SemVerEvent.of(EventSpecifier.introduced, "1.0.0");
    SemVerEvent fixed1 = SemVerEvent.of(EventSpecifier.fixed, "1.14.14");
    SemVerEvent introduced2 = SemVerEvent.of(EventSpecifier.introduced, "1.15.0");
    SemVerEvent fixed2 = SemVerEvent.of(EventSpecifier.fixed, "1.15.17");
    Ranges ranges = TypeSemVer.of(introduced1, fixed1, introduced2, fixed2);
    return new AffectedBuilder(pckg).ranges(ranges).build();
  }

  private static Affected affectedWithVersion() {
    Package pckg = Package.of(Ecosystem.Go, Name.of("crypto/elliptic"));
    Versions version = Versions.of("2.8.0", "2.8.0.post1", "2.8.0.post2", "2.9.0", "2.9.1",
        "2.9.2");
    return new AffectedBuilder(pckg).versions(version).build();
  }

  private static Set<Affected> deserialize(String jsonData)
      throws JsonProcessingException, OsvParserException {
    return AffectedDeserializer.deserialize(toJsonNode(jsonData));
  }

  private static JsonNode toJsonNode(String jsonData) throws JsonProcessingException {
    return mapper.readTree(jsonData);
  }
}
