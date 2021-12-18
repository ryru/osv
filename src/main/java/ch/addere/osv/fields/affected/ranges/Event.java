package ch.addere.osv.fields.affected.ranges;

import ch.addere.osv.fields.affected.ranges.events.EventSpecifier;

/**
 * Event specifies on what versions an event was introduced or fixed.
 */
public interface Event {

  String EVENTS_KEY = "events";

  EventSpecifier event();

  String release();

}
