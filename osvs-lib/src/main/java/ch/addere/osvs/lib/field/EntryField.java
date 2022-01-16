package ch.addere.osvs.lib.field;

/**
 * Generic key value interface.
 *
 * @param <T> OSVS key value field
 */
interface EntryField<T> {

  /**
   * Key of this vulnerability entry.
   *
   * @return vulnerability entry key
   */
  String getKey();

  /**
   * Value of this vulnerability entry.
   *
   * @return vulnerability entry value
   */
  T getValue();
}
