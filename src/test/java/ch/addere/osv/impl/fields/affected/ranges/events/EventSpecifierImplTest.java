package ch.addere.osv.impl.fields.affected.ranges.events;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.affected.ranges.events.EventSpecifier;
import org.junit.jupiter.api.Test;

class EventSpecifierImplTest {

  private static final String FIXED = "fixed";

  @Test
  void testValidFixed() {
    EventSpecifier eventSpecifier = EventSpecifierImpl.FIXED;
    assertThat(eventSpecifier.value()).isEqualTo(FIXED);
  }

  @Test
  void testValidOfFixed() {
    EventSpecifier ecosystem = EventSpecifierImpl.of(FIXED);
    assertThat(ecosystem.value()).isEqualTo(FIXED);
  }

  @Test
  void testInvalidOfNonExisting() {
    assertThatThrownBy(() -> EventSpecifierImpl.of("NonExisting"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("'NonExisting' is not a valid event specifier");
  }

  @Test
  void testOfValueObjectCreation() {
    EventSpecifier eventSpecifier1 = EventSpecifierImpl.FIXED;
    EventSpecifier eventSpecifier2 = EventSpecifierImpl.of(eventSpecifier1.value());
    assertThat(eventSpecifier1).isEqualTo(eventSpecifier2);
  }

  @Test
  void testIntroduced() {
    EventSpecifier eventSpecifier = EventSpecifierImpl.INTRODUCED;
    String eventSpec = eventSpecifier.toString();
    assertThat(eventSpec).isEqualTo("introduced");
  }

  @Test
  void testFixed() {
    EventSpecifier eventSpecifier = EventSpecifierImpl.FIXED;
    String eventSpec = eventSpecifier.toString();
    assertThat(eventSpec).isEqualTo(FIXED);
  }

  @Test
  void testLimited() {
    EventSpecifier eventSpecifier = EventSpecifierImpl.LIMITED;
    String eventSpec = eventSpecifier.toString();
    assertThat(eventSpec).isEqualTo("limited");
  }
}
