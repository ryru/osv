package ch.addere.osv.impl.fields.affected.ranges.events;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class EventSpecifierTest {

  private static final String FIXED = "fixed";

  @Test
  void testValidFixed() {
    EventSpecifierValue eventSpecifier = EventSpecifierValue.FIXED;
    assertThat(eventSpecifier.value()).isEqualTo(FIXED);
  }

  @Test
  void testValidOfFixed() {
    EventSpecifierValue ecosystem = EventSpecifierValue.of(FIXED);
    assertThat(ecosystem.value()).isEqualTo(FIXED);
  }

  @Test
  void testInvalidOfNonExisting() {
    assertThatThrownBy(() -> EventSpecifierValue.of("NonExisting"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("'NonExisting' is not a valid event specifier");
  }

  @Test
  void testOfValueObjectCreation() {
    EventSpecifierValue eventSpecifier1 = EventSpecifierValue.FIXED;
    EventSpecifierValue eventSpecifier2 = EventSpecifierValue.of(eventSpecifier1.value());
    assertThat(eventSpecifier1).isEqualTo(eventSpecifier2);
  }

  @Test
  void testIntroduced() {
    EventSpecifierValue eventSpecifier = EventSpecifierValue.INTRODUCED;
    String eventSpec = eventSpecifier.toString();
    assertThat(eventSpec).isEqualTo("introduced");
  }

  @Test
  void testFixed() {
    EventSpecifierValue eventSpecifier = EventSpecifierValue.FIXED;
    String eventSpec = eventSpecifier.toString();
    assertThat(eventSpec).isEqualTo(FIXED);
  }

  @Test
  void testLimited() {
    EventSpecifierValue eventSpecifier = EventSpecifierValue.LIMITED;
    String eventSpec = eventSpecifier.toString();
    assertThat(eventSpec).isEqualTo("limited");
  }
}
