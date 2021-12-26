package ch.addere.osv.impl.fields.affected;

import static ch.addere.osv.impl.fields.affected.VersionsValue.VERSIONS_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class VersionsValueTest {

  private static final String ENTRY_1 = "entry1";
  private static final String ENTRY_2 = "entry2";

  @Test
  void testJsonKey() {
    assertThat(VERSIONS_KEY).isEqualTo("versions");
  }

  @Test
  void testOfNull() {
    assertThatThrownBy(() -> VersionsValue.of((String[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument versions must not be null");
  }

  @Test
  void testOfValueObjectCreation() {
    VersionsValue versions1 = VersionsValue.of(ENTRY_1);
    VersionsValue versions2 = VersionsValue.of(versions1.value().toArray(new String[0]));
    assertThat(versions1).isEqualTo(versions2);
  }

  @Test
  void testEmptyVersions() {
    VersionsValue versions = VersionsValue.of();
    assertThat(versions.value()).asList().isEmpty();
  }

  @Test
  void testSingleVersions() {
    VersionsValue versions = VersionsValue.of(ENTRY_1);
    assertThat(versions.value()).asList().containsExactly(ENTRY_1);
  }

  @Test
  void testMultipleVersions() {
    VersionsValue versions = VersionsValue.of(ENTRY_1, ENTRY_2);
    assertThat(versions.value()).asList().containsExactly(ENTRY_1, ENTRY_2);
  }

  @Test
  void testEquality() {
    VersionsValue versions = VersionsValue.of(ENTRY_1);
    VersionsValue otherVersions = VersionsValue.of(ENTRY_1);
    assertThat(versions).satisfies(v -> {
      assertThat(v).isEqualTo(versions);
      assertThat(v).isEqualTo(otherVersions);
    });
  }

  @Test
  void testNonEquality() {
    VersionsValue versions = VersionsValue.of(ENTRY_1);
    VersionsValue otherVersions = VersionsValue.of(ENTRY_2);
    assertThat(versions).satisfies(v -> {
      assertThat(v).isNotEqualTo(null);
      assertThat(v).isNotEqualTo(otherVersions);
    });
  }

  @Test
  void testHashCode() {
    VersionsValue versions = VersionsValue.of(ENTRY_1);
    VersionsValue otherVersions = VersionsValue.of(ENTRY_1);
    assertThat(versions).hasSameHashCodeAs(otherVersions);
  }

  @Test
  void testToString() {
    VersionsValue versions = VersionsValue.of(ENTRY_1, ENTRY_2);
    assertThat(versions).hasToString(VERSIONS_KEY + ": " + ENTRY_1 + ", " + ENTRY_2);
  }
}
