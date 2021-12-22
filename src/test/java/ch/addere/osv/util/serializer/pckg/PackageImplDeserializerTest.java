package ch.addere.osv.util.serializer.pckg;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.fields.affected.Package;
import ch.addere.osv.impl.fields.affected.PackageImpl;
import ch.addere.osv.impl.fields.affected.pckg.EcosystemValue;
import ch.addere.osv.impl.fields.affected.pckg.NameValue;
import ch.addere.osv.impl.fields.affected.pckg.PurlValue;
import ch.addere.osv.util.OsvParserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class PackageImplDeserializerTest {

  private static final ObjectMapper mapper = new ObjectMapper();
  private static final String PURL_STRING = "pkg:docker/cassandra@sha256:244fd47e07d1004f0aed9c";

  @Test
  void testInvalidJsonInput() {
    assertThatThrownBy(() -> deserialize(""))
        .isInstanceOf(OsvParserException.class)
        .hasMessageContaining("package node is not a valid node");
  }

  @Test
  void testValidMinimalGoPackage() throws JsonProcessingException, OsvParserException {
    Package pckg = PackageImpl.of(EcosystemValue.GO, NameValue.fromString("crypto/elliptic"));
    Package deserializedPackage = deserialize(
        "{\"ecosystem\":\"Go\",\"name\":\"crypto/elliptic\"}");
    assertThat(deserializedPackage).isEqualTo(pckg);
  }

  @Test
  void testValidGoPackage() throws JsonProcessingException, OsvParserException {
    Package pckg = PackageImpl.of(EcosystemValue.GO, NameValue.fromString("crypto/elliptic"),
        PurlValue.fromString(PURL_STRING));
    Package deserializedPackage = deserialize(
        "{\"ecosystem\":\"Go\",\"name\":\"crypto/elliptic\",\"purl\":\""
            + PURL_STRING + "\"}");
    assertThat(deserializedPackage).isEqualTo(pckg);
  }

  private static Package deserialize(String jsonData)
      throws JsonProcessingException, OsvParserException {
    return PackageDeserializer.deserialize(toJsonNode(jsonData));
  }

  private static JsonNode toJsonNode(String jsonData) throws JsonProcessingException {
    return mapper.readTree(jsonData);
  }
}
