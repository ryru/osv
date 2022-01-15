package ch.addere.osv.spec;

/**
 * This test class address additional constrains, not already covered by the value property created,
 * that are required by the specification as of version 1.1.0 (December 15, 2021).
 */
public class SpecVersion110Test {

  //------------------------------------------------------------------------------------------------
  // schema_version field
  //------------------------------------------------------------------------------------------------

  // In short, each object in the `affected` array must contain either a non-empty `versions` list
  // or at least one range in the `ranges` list of type `SEMVER`.
}
