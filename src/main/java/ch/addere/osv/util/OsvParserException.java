package ch.addere.osv.util;

import java.io.IOException;

/**
 * Exception for all kind of JSON OSV parsing.
 */
public final class OsvParserException extends IOException {

  public OsvParserException(String message) {
    super(message);
  }

  public OsvParserException(String message, Throwable cause) {
    super(message, cause);
  }
}
