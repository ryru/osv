package ch.addere.osv.impl.fields.affected.ranges.events;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class EventSpecifierTest {

  @Test
  void testIntroduced() {
    EventSpecifier eventSpecifier = EventSpecifier.introduced;
    String eventSpec = eventSpecifier.toString();
    assertThat(eventSpec).isEqualTo("introduced");
  }

  @Test
  void testFixed() {
    EventSpecifier eventSpecifier = EventSpecifier.fixed;
    String eventSpec = eventSpecifier.toString();
    assertThat(eventSpec).isEqualTo("fixed");
  }

  @Test
  void testLimited() {
    EventSpecifier eventSpecifier = EventSpecifier.limited;
    String eventSpec = eventSpecifier.toString();
    assertThat(eventSpec).isEqualTo("limited");
  }
}
