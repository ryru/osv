package ch.addere.osv.util.serializer.pckg;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.property.affected.PackageValues;
import ch.addere.osv.property.affected.PackageValues.PackageValueBuilder;
import ch.addere.osv.property.affected.pckg.EcosystemValue;
import ch.addere.osv.property.affected.pckg.NameValue;
import ch.addere.osv.property.affected.pckg.PurlValue;
import ch.addere.osv.util.OsvParserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class PackageValuesDeserializerTest {

  private static final ObjectMapper mapper = new ObjectMapper();
  private static final String PURL_STRING = "pkg:docker/cassandra@sha256:244fd47e07d1004f0aed9c";

  @Test
  void testInvalidJsonInput() {
    assertThatThrownBy(() -> deserialize(""))
        .isInstanceOf(OsvParserException.class)
        .hasMessageContaining("package node is not a valid node");
  }

  private static PackageValues deserialize(String jsonData)
      throws JsonProcessingException, OsvParserException {
    return PackageDeserializer.deserialize(toJsonNode(jsonData));
  }

  private static PackageValueBuilder builder(EcosystemValue ecosystem, NameValue name) {
    return new PackageValueBuilder(ecosystem, name);
  }

  @Test
  void testValidMinimalGoPackage() throws JsonProcessingException, OsvParserException {
    PackageValues pckg = builder(EcosystemValue.GO, NameValue.fromString("crypto/elliptic"))
        .build();
    PackageValues deserializedPackage = deserialize(
        "{\"ecosystem\":\"Go\",\"name\":\"crypto/elliptic\"}");
    assertThat(deserializedPackage).isEqualTo(pckg);
  }

  private static JsonNode toJsonNode(String jsonData) throws JsonProcessingException {
    return mapper.readTree(jsonData);
  }

  @Test
  void testValidGoPackage() throws JsonProcessingException, OsvParserException {
    PackageValues pckg = builder(EcosystemValue.GO, NameValue.fromString("crypto/elliptic"))
        .purl(PurlValue.fromString(PURL_STRING))
        .build();
    PackageValues deserializedPackage = deserialize(
        "{\"ecosystem\":\"Go\",\"name\":\"crypto/elliptic\",\"purl\":\""
            + PURL_STRING + "\"}");
    assertThat(deserializedPackage).isEqualTo(pckg);
  }
}
