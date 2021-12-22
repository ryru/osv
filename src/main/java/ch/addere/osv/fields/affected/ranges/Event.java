package ch.addere.osv.fields.affected.ranges;

import ch.addere.osv.impl.fields.affected.ranges.events.EventSpecifierValue;

/**
 * Event specifies on what versions an event was introduced or fixed.
 */
public interface Event {

  String EVENTS_KEY = "events";

  EventSpecifierValue event();

  String release();

}
