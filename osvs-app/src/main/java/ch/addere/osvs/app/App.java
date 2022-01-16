package ch.addere.osvs.app;


import ch.addere.osvs.lib.field.IdField;
import ch.addere.osvs.lib.field.types.IdDb;

public class App {

  public static void main(String[] args)  {
    IdDb db = IdDb.GSD;
    IdField id = IdField.of(db, "someId42");
    IdField id2 = IdField.of(db, "", "1.1.0");
    System.out.println(id);
    System.out.println(id2);
  }

}
