package ch.addere.osv.domain.service;

import java.io.IOException;

/**
 * Exception for all kind of JSON OSV parsing.
 */
public class ParserException extends IOException {

  public ParserException(String message) {
    super(message);
  }
}
