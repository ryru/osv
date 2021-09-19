package ch.addere.osv.domain.model.fields;

/**
 * All open source vulnerability fields are writable to JSON. This is guaranteed by this interface.
 */
public interface Jsonable {

  /**
   * An object can be written to a JSON value.
   *
   * @return a string of an object representation as JSON value
   */
  String toJson();

}
