package ch.addere.osvs.lib.field.types;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SchemaVersionTest {

  @Test
  void testVersion100() {
    assertThat(SchemaVersion.V_1_0_0).hasToString("1.0.0");
  }

  @Test
  void testVersion110() {
    assertThat(SchemaVersion.V_1_1_0).hasToString("1.1.0");
  }

  @Test
  void testVersion120() {
    assertThat(SchemaVersion.V_1_2_0).hasToString("1.2.0");
  }
}
