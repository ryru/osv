package ch.addere.osvs.app;


import ch.addere.osvs.lib.field.Id;

public class App {

  public static void main(String[] args) {
    Id id = Id.of("test id");
    System.out.println(id.getKey() + " " + id.getValue());
  }

}
