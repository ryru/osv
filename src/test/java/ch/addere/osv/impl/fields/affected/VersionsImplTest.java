package ch.addere.osv.impl.fields.affected;

import static ch.addere.osv.impl.fields.affected.VersionsImpl.VERSIONS_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.affected.Versions;
import org.junit.jupiter.api.Test;

class VersionsImplTest {

  private static final String ENTRY_1 = "entry1";
  private static final String ENTRY_2 = "entry2";

  @Test
  void testJsonKey() {
    assertThat(VERSIONS_KEY).isEqualTo("versions");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> VersionsImpl.of((String[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument versions must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    Versions versions1 = VersionsImpl.of(ENTRY_1);
    Versions versions2 = VersionsImpl.of(versions1.value().toArray(new String[0]));
    assertThat(versions1).isEqualTo(versions2);
  }

  @Test
  void testEmptyVersions() {
    Versions versions = VersionsImpl.of();
    assertThat(versions.value()).asList().isEmpty();
  }

  @Test
  void testSingleVersions() {
    Versions versions = VersionsImpl.of(ENTRY_1);
    assertThat(versions.value()).asList().containsExactly(ENTRY_1);
  }

  @Test
  void testMultipleVersions() {
    Versions versions = VersionsImpl.of(ENTRY_1, ENTRY_2);
    assertThat(versions.value()).asList().containsExactly(ENTRY_1, ENTRY_2);
  }

  @Test
  void testEquality() {
    Versions versions = VersionsImpl.of(ENTRY_1);
    Versions otherVersions = VersionsImpl.of(ENTRY_1);
    assertThat(versions).isEqualTo(otherVersions);
  }

  @Test
  void testNonEquality() {
    Versions versions = VersionsImpl.of(ENTRY_1);
    Versions otherVersions = VersionsImpl.of(ENTRY_2);
    assertThat(versions).isNotEqualTo(otherVersions);
  }

  @Test
  void testHashCode() {
    Versions versions = VersionsImpl.of(ENTRY_1);
    Versions otherVersions = VersionsImpl.of(ENTRY_1);
    assertThat(versions).hasSameHashCodeAs(otherVersions);
  }

  @Test
  void testToString() {
    Versions versions = VersionsImpl.of(ENTRY_1, ENTRY_2);
    assertThat(versions).hasToString(VERSIONS_KEY + ": " + ENTRY_1 + ", " + ENTRY_2);
  }
}
