package ch.addere.osv.domain.model.fields.affected;

import static ch.addere.osv.domain.model.fields.affected.Versions.VERSIONS_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class VersionsTest {

  private static final String ENTRY_1 = "entry1";
  private static final String ENTRY_2 = "entry2";

  @Test
  void testJsonKey() {
    assertThat(VERSIONS_KEY).isEqualTo("versions");
  }

  @Test
  void testEmptyVersions() {
    var versions = Versions.of();
    assertThat(versions.versions()).asList().isEmpty();
  }

  @Test
  void testSingleVersions() {
    var versions = Versions.of(ENTRY_1);
    assertThat(versions.versions()).asList().containsExactly(ENTRY_1);
  }

  @Test
  void testMultipleVersions() {
    var versions = Versions.of(ENTRY_1, ENTRY_2);
    assertThat(versions.versions()).asList().containsExactly(ENTRY_1, ENTRY_2);
  }

  @Test
  void testSameness() {
    Versions versions = Versions.of(ENTRY_1);
    Versions otherVersions = versions;
    assertThat(versions).isEqualTo(otherVersions);
  }

  @Test
  void testEquality() {
    Versions versions = Versions.of(ENTRY_1);
    Versions otherVersions = Versions.of(ENTRY_1);
    assertThat(versions).isEqualTo(otherVersions);
  }

  @Test
  void testNonEquality() {
    Versions versions = Versions.of(ENTRY_1);
    Versions otherVersions = Versions.of(ENTRY_2);
    assertThat(versions).isNotEqualTo(otherVersions);
  }

  @Test
  void testHashCode() {
    Versions versions = Versions.of(ENTRY_1);
    Versions otherVersions = Versions.of(ENTRY_1);
    assertThat(versions.hashCode()).isEqualTo(otherVersions.hashCode());
  }

  @Test
  void testToString() {
    Versions versions = Versions.of(ENTRY_1, ENTRY_2);
    assertThat(versions.toString()).isEqualTo(VERSIONS_KEY + ": " + ENTRY_1 + ", " + ENTRY_2);
  }
}
