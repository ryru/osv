package ch.addere.osvs.app;


import ch.addere.osvs.lib.field.*;
import ch.addere.osvs.lib.field.types.IdDb;
import ch.addere.osvs.lib.field.types.Reference;
import ch.addere.osvs.lib.field.types.ReferencesEntry;

import java.net.MalformedURLException;
import java.net.URL;

public class App {

  public static void main(String[] args) {
    IdDb db = IdDb.GSD;
    IdField id = IdField.of(db, "someId42");
    IdField id2 = IdField.of("1.1.0", db, "");
    System.out.println(id);
    System.out.println(id2);

    AliasesField aliases = AliasesField.of(id, id2);
    System.out.println(aliases);

    ReferencesEntry refEntry1 = ReferencesEntry.of(refType(), refUrl());
    ReferencesEntry refEntry2 = ReferencesEntry.of(refType(), refUrl());
    ReferencesEntry refEntry3 = ReferencesEntry.of(refType(), refUrl());
    ReferencesField referencesField = ReferencesField.of(refEntry1, refEntry2, refEntry3);
    System.out.println(referencesField);
    System.out.println("---");
    System.out.println("key = " + refType().getKey() + ", value = " + refType().getValue());
    System.out.println("key = " + refUrl().getKey() + ", value = " + refUrl().getValue());
    System.out.println("key = " + referencesField.getKey() + ", value = " + referencesField.getValue());
  }

  private static ReferencesTypeField refType() {
    return ReferencesTypeField.of(Reference.REPORT);
  }

  private static ReferencesUrlField refUrl() throws MalformedURLException {
    URL url = new URL("https://osv.dev");
    return ReferencesUrlField.of(url);
  }
}
