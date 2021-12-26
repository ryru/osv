package ch.addere.osv.util.serializer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.fields.Affected;
import ch.addere.osv.fields.affected.Package;
import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.impl.fields.AffectedImpl.AffectedBuilder;
import ch.addere.osv.impl.fields.affected.PackageImpl;
import ch.addere.osv.impl.fields.affected.VersionsValue;
import ch.addere.osv.impl.fields.affected.pckg.EcosystemValue;
import ch.addere.osv.impl.fields.affected.pckg.NameValue;
import ch.addere.osv.impl.fields.affected.pckg.PurlValue;
import ch.addere.osv.impl.fields.affected.ranges.TypeSemVerImpl;
import ch.addere.osv.impl.fields.affected.ranges.events.EventSpecifierValue;
import ch.addere.osv.impl.fields.affected.ranges.events.SemVerEvent;
import ch.addere.osv.util.OsvParserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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
          + "  \"purl\": \"pkg:deb/debian/curl@7.50.3-1?arch=i386&distro=jessie\""
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
  private static final String PURL_STRING = "pkg:deb/debian/curl@7.50.3-1?arch=i386&distro=jessie";

  @Test
  void testInvalidJsonInput() {
    assertThatThrownBy(() -> deserialize(""))
        .isInstanceOf(OsvParserException.class)
        .hasMessageContaining("affected node is not an array node");
  }

  @Test
  void testValidAffected() throws JsonProcessingException, OsvParserException {
    Affected affected = affected(null);
    List<Affected> deserializedAffected = deserialize(AFFECTED);
    assertThat(deserializedAffected).containsExactly(affected);
  }

  private static Affected affected(PurlValue purl) {
    Package pckg = PackageImpl.of(EcosystemValue.GO, NameValue.fromString("crypto/elliptic"), purl);
    SemVerEvent introduced1 = SemVerEvent.of(EventSpecifierValue.INTRODUCED, "1.0.0");
    SemVerEvent fixed1 = SemVerEvent.of(EventSpecifierValue.FIXED, "1.14.14");
    SemVerEvent introduced2 = SemVerEvent.of(EventSpecifierValue.INTRODUCED, "1.15.0");
    SemVerEvent fixed2 = SemVerEvent.of(EventSpecifierValue.FIXED, "1.15.17");
    Ranges ranges = TypeSemVerImpl.of(introduced1, fixed1, introduced2, fixed2);
    return new AffectedBuilder(pckg).ranges(ranges).build();
  }

  @Test
  void testValidAffectedWithVerson() throws JsonProcessingException, OsvParserException {
    Affected affected = affectedWithVersion();
    List<Affected> deserializedAffected = deserialize(AFFECTED_WITH_VERSION);
    assertThat(deserializedAffected).containsExactly(affected);
  }

  private static Affected affectedWithVersion() {
    Package pckg = PackageImpl.of(EcosystemValue.GO, NameValue.fromString("crypto/elliptic"));
    VersionsValue version = VersionsValue.of("2.8.0", "2.8.0.post1", "2.8.0.post2", "2.9.0",
        "2.9.1", "2.9.2");
    return new AffectedBuilder(pckg).versions(version).build();
  }

  @Test
  void testValidAffectedWithPurl() throws JsonProcessingException, OsvParserException {
    Affected affected = affected(PurlValue.fromString(PURL_STRING));
    List<Affected> deserializedAffected = deserialize(AFFECTED_WITH_PURL);
    assertThat(deserializedAffected).containsExactly(affected);
  }

  private static List<Affected> deserialize(String jsonData)
      throws JsonProcessingException, OsvParserException {
    return AffectedDeserializer.deserialize(toJsonNode(jsonData));
  }

  private static JsonNode toJsonNode(String jsonData) throws JsonProcessingException {
    return mapper.readTree(jsonData);
  }
}
