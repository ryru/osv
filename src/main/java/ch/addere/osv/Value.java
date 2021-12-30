package ch.addere.osv;

/**
 * Value of a vulnerability value property.
 */
public interface Value<T> {

  /**
   * Current value of this vulnerability propety.
   *
   * @return value
   */
  T value();

}
