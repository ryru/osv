package ch.addere.osv.property.affected.ranges;

import ch.addere.osv.property.affected.ranges.events.EventSpecifierValue;

/**
 * Event specifies on what versions an event was introduced or fixed.
 */
public interface Event {

  String EVENTS_KEY = "events";

  EventSpecifierValue event();

  String version();

}
