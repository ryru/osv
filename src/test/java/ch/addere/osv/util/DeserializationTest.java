package ch.addere.osv.util;

import static java.nio.file.Files.readString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.Entry;
import ch.addere.osv.property.AffectedValues.AffectedValuesBuilder;
import ch.addere.osv.property.AliasesValue;
import ch.addere.osv.property.DetailsValue;
import ch.addere.osv.property.IdDatabaseValue;
import ch.addere.osv.property.IdValue;
import ch.addere.osv.property.ModifiedValue;
import ch.addere.osv.property.PublishedValue;
import ch.addere.osv.property.ReferencesValues;
import ch.addere.osv.property.RelatedValue;
import ch.addere.osv.property.SummaryValue;
import ch.addere.osv.property.affected.DatabaseSpecificValue;
import ch.addere.osv.property.affected.EcosystemSpecificValue;
import ch.addere.osv.property.affected.PackageValues.PackageValueBuilder;
import ch.addere.osv.property.affected.VersionsValue;
import ch.addere.osv.property.affected.pckg.EcosystemValue;
import ch.addere.osv.property.affected.pckg.NameValue;
import ch.addere.osv.property.affected.ranges.RepoValue;
import ch.addere.osv.property.affected.ranges.TypeEcosystemValues.TypeEcosystemBuilder;
import ch.addere.osv.property.affected.ranges.TypeGitValues.TypeGitBuilder;
import ch.addere.osv.property.affected.ranges.TypeSemVerValues.TypeSemVerBuilder;
import ch.addere.osv.property.affected.ranges.events.EcosystemEventValues;
import ch.addere.osv.property.affected.ranges.events.EventSpecifierValue;
import ch.addere.osv.property.affected.ranges.events.GitEventValues;
import ch.addere.osv.property.affected.ranges.events.SemVerEventValues;
import ch.addere.osv.property.references.ReferenceTypeValue;
import ch.addere.osv.property.references.ReferenceUrlValue;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class DeserializationTest {

  private static final String EMPTY_FILE = "";
  private static final String EMPTY_JSON = "{}";
  private static final String MINIMAL_OSV = "{\"id\":\"GO-2021-99998\","
      + "\"modified\":\"2021-03-10T23:20:53Z\"}";
  private static final String GO_EXAMPLE_PATH = "src/test/resources/go-example.json";
  private static final String GO_TOOL_EXAMPLE_PATH = "src/test/resources/go-tool-example.json";
  private static final String NPM_EXAMPLE_PATH = "src/test/resources/npm-example.json";
  private static final String OSV_EXAMPLE_PATH = "src/test/resources/osv-example.json";
  private static final String PYTHON_EXAMPLE_PATH = "src/test/resources/python-example.json";
  private static final String RUBY_EXAMPLE_PATH = "src/test/resources/ruby-example.json";
  private static final String RUST_EXAMPLE_PATH = "src/test/resources/rust-example.json";

  private static Entry deserialize(String jsonData) throws OsvParserException {
    return Deserializer.fromJson(jsonData);
  }

  private static String readJsonFile(String path) {
    var fileName = Path.of(path);
    try {
      return readString(fileName);
    } catch (IOException e) {
      return null;
    }
  }

  private static Entry minimalEntry() {
    return Entry.builder(
            IdValue.of(IdDatabaseValue.GO, "2021-99998"),
            ModifiedValue.of(Instant.parse("2021-03-10T23:20:53Z")))
        .build();
  }

  private static Entry goEntry() throws MalformedURLException {
    return Entry.builder(
            IdValue.of(IdDatabaseValue.GO, "2021-99998"),
            ModifiedValue.of(Instant.parse("2021-03-10T23:20:53Z")))
        .published(PublishedValue.of(Instant.parse("2021-01-21T19:15:00Z")))
        .aliases(AliasesValue.of(IdValue.of(IdDatabaseValue.CVE, "2021-3114")))
        .summary(SummaryValue.fromString("incorrect P-224 curve operations"))
        .details(DetailsValue.fromString(
            "The P224() Curve implementation can in rare circumstances generate incorrect outputs, "
                + "including returning invalid points from ScalarMult.\n\nThe crypto/x509 and "
                + "golang.org/x/crypto/ocsp (but not crypto/tls) packages support P-224 ECDSA "
                + "keys, but they are not supported by publicly trusted certificate authorities. "
                + "No other standard library or golang.org/x/crypto package supports or uses the "
                + "P-224 curve.\n\nThe incorrect output was found by the "
                + "elliptic-curve-differential-fuzzer project running on OSS-Fuzz and reported by "
                + "Philippe Antoine (Catena cyber)."))
        .references(
            ReferencesValues.of(ReferenceTypeValue.REPORT,
                ReferenceUrlValue.of(new URL("https://golang.org/issue/43786"))),
            ReferencesValues.of(ReferenceTypeValue.WEB, ReferenceUrlValue.of(
                new URL("https://github.com/catenacyber/elliptic-curve-differential-fuzzer")))
        )
        .affected(new AffectedValuesBuilder(
            builder(EcosystemValue.GO, NameValue.fromString("crypto/elliptic")).build())
            .ranges(new TypeSemVerBuilder(
                SemVerEventValues.of(EventSpecifierValue.INTRODUCED, "1.0.0"),
                SemVerEventValues.of(EventSpecifierValue.FIXED, "1.14.14"),
                SemVerEventValues.of(EventSpecifierValue.INTRODUCED, "1.15.0"),
                SemVerEventValues.of(EventSpecifierValue.FIXED, "1.15.17")
            ).build())
            .ecosystemSpecific(EcosystemSpecificValue.fromString(
                "{\"functions\":[\"P224\"],\"module\":\"std\",\"severity\":\"HIGH\"}"))
            .build())
        .build();
  }

  private static Entry goToolEntry() throws MalformedURLException {
    return Entry.builder(
            IdValue.of(IdDatabaseValue.GO, "2021-99999"),
            ModifiedValue.of(Instant.parse("2021-03-10T23:20:53Z"))
        )
        .published(PublishedValue.of(Instant.parse("2021-01-21T19:15:00Z")))
        .aliases(AliasesValue.of(IdValue.of(IdDatabaseValue.CVE, "2021-3115")))
        .summary(
            SummaryValue.fromString(
                "packages using cgo can cause arbitrary code execution at build time"))
        .details(DetailsValue.fromString(
            "The go command may execute arbitrary code at build time when cgo is in use on "
                + "Windows. This may occur when running \"go get\", or any other command that "
                + "builds code. Only users who build untrusted code (and don’t execute it) are "
                + "affected.\n\nIn addition to Windows users, this can also affect Unix users who "
                + "have \".\" listed explicitly in their PATH and are running \"go get\" or build "
                + "commands outside of a module or with module mode disabled.\n\nThanks to RyotaK "
                + "(https://twitter.com/ryotkak) for reporting this issue."))
        .references(ReferencesValues.of(ReferenceTypeValue.REPORT,
            ReferenceUrlValue.of(new URL("https://golang.org/issue/43783"))))
        .affected(
            new AffectedValuesBuilder(
                builder(EcosystemValue.GO, NameValue.fromString("cmd/go")).build())
                .ranges(new TypeSemVerBuilder(
                    SemVerEventValues.of(EventSpecifierValue.INTRODUCED, "1.0.0"),
                    SemVerEventValues.of(EventSpecifierValue.FIXED, "1.14.14"),
                    SemVerEventValues.of(EventSpecifierValue.INTRODUCED, "1.15.10"),
                    SemVerEventValues.of(EventSpecifierValue.FIXED, "1.15.17")
                ).build())
                .ecosystemSpecific(EcosystemSpecificValue.fromString(
                    "{\"severity\":\"HIGH\"}"))
                .build())
        .build();
  }

  private static Entry npmEntry() throws MalformedURLException {
    return Entry.builder(
            IdValue.of(IdDatabaseValue.GHSA, "r9p9-mrjm-926w"),
            ModifiedValue.of(Instant.parse("2021-03-10T23:40:39Z"))
        )
        .published(PublishedValue.of(Instant.parse("2021-03-07T11:27:00Z")))
        .aliases(
            AliasesValue.of(
                IdValue.of(IdDatabaseValue.NPM, "1648"),
                IdValue.of(IdDatabaseValue.CVE, "2020-28498"),
                IdValue.of(IdDatabaseValue.SNYK, "JS-ELLIPTIC-1064899"))
        )
        .related(
            RelatedValue.of(
                IdValue.of(IdDatabaseValue.NPM, "1649"),
                IdValue.of(IdDatabaseValue.SNYK, "JAVA-ORGWEBJARSNPM-1069836"))
        )
        .summary(SummaryValue.fromString("Use of a Broken or Risky Cryptographic Algorithm"))
        .details(DetailsValue.fromString(
            "elliptic is a Fast elliptic-curve cryptography in a plain javascript implementation."
                + "\n\nAffected versions of this package are vulnerable to Cryptographic Issues "
                + "via the secp256k1 implementation in elliptic/ec/key.js. There is no check to "
                + "confirm that the public key point passed into the derive function actually "
                + "exists on the secp256k1 curve. This results in the potential for the private "
                + "key used in this implementation to be revealed after a number of ECDH "
                + "operations are performed.\n\nRemediation: Upgrade elliptic to version 6.5.4 or "
                + "higher.\n"))
        .references(
            ReferencesValues.of(ReferenceTypeValue.ADVISORY,
                ReferenceUrlValue.of(new URL("https://www.npmjs.com/advisories/1648"))),
            ReferencesValues.of(ReferenceTypeValue.ADVISORY,
                ReferenceUrlValue.of(
                    new URL("https://nvd.nist.gov/vuln/detail/CVE-2020-28498"))),
            ReferencesValues.of(ReferenceTypeValue.FIX,
                ReferenceUrlValue.of(
                    new URL("https://github.com/indutny/elliptic/commit/441b7428"))),
            ReferencesValues.of(ReferenceTypeValue.ARTICLE,
                ReferenceUrlValue.of(new URL(
                    "https://github.com/christianlundkvist/blog/blob/master/"
                        + "2020_05_26_secp256k1_twist_attacks/secp256k1_twist_attacks.md"))),
            ReferencesValues.of(ReferenceTypeValue.ADVISORY,
                ReferenceUrlValue.of(
                    new URL("https://snyk.io/vuln/SNYK-JS-ELLIPTIC-1064899"))),
            ReferencesValues.of(ReferenceTypeValue.PACKAGE,
                ReferenceUrlValue.of(new URL("https://www.npmjs.com/package/elliptic")))
        )
        .affected(
            new AffectedValuesBuilder(
                builder(EcosystemValue.NPM, NameValue.fromString("elliptic")).build())
                .ranges(new TypeSemVerBuilder(
                    SemVerEventValues.of(EventSpecifierValue.INTRODUCED, "1.15.0"),
                    SemVerEventValues.of(EventSpecifierValue.FIXED, "1.15.17"),
                    SemVerEventValues.of(EventSpecifierValue.INTRODUCED, "6.5.0"),
                    SemVerEventValues.of(EventSpecifierValue.FIXED, "6.5.4")
                ).build())
                .databaseSpecific(DatabaseSpecificValue.fromString(
                    "{\"CWE\":\"CWE-327\",\"CVSS\":{\"Score\":\"6.8\",\"Severity\":\"Medium\","
                        + "\"Code\":\"CVSS:3.1/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:N/A:N\"}}"
                ))
                .build())
        .build();
  }

  private static Entry osvEntry() throws MalformedURLException {
    return Entry.builder(
            IdValue.of(IdDatabaseValue.OSV, "2020-584"),
            ModifiedValue.of(Instant.parse("2021-03-09T04:49:05.965964Z"))
        )
        .published(PublishedValue.of(Instant.parse("2020-07-01T00:00:18.401815Z")))
        .summary(SummaryValue.fromString("Heap-buffer-overflow in collator_compare_fuzzer.cpp"))
        .details(DetailsValue.fromString(
            "OSS-Fuzz report: https://bugs.chromium.org/p/oss-fuzz/issues/detail?id=15499\n\nCrash "
                + "type: Heap-buffer-overflow WRITE 3\nCrash state:\ncollator_compare_fuzzer"
                + ".cpp\n"))
        .references(ReferencesValues.of(
            ReferenceTypeValue.REPORT, ReferenceUrlValue.of(
                new URL("https://bugs.chromium.org/p/oss-fuzz/issues/detail?id=15499"))
        ))
        .affected(
            new AffectedValuesBuilder(
                builder(EcosystemValue.OSS_FUZZ, NameValue.fromString("icu")).build())
                .ranges(
                    new TypeGitBuilder(RepoValue.of(
                        new URL("https://github.com/unicode-org/icu.git")),
                        GitEventValues.of(EventSpecifierValue.INTRODUCED,
                            "6e5755a2a833bc64852eae12967d0a54d7adf629"),
                        GitEventValues.of(EventSpecifierValue.FIXED,
                            "c43455749b914feef56b178b256f29b3016146eb")
                    ).build()
                )
                .versions(VersionsValue.of("aVersion"))
                .ecosystemSpecific(EcosystemSpecificValue.fromString("{\"severity\":\"HIGH\"}"))
                .databaseSpecific(DatabaseSpecificValue.fromString(
                    "{\"source\":\"https://github.com/google/oss-fuzz-vulns/blob/main/vulns/icu/"
                        + "OSV-2020-584.yaml\"}"))
                .build()
        )
        .build();
  }

  private static Entry pythonEntry() throws MalformedURLException {
    return Entry.builder(
            IdValue.of(IdDatabaseValue.PYSEC, "2021-XXXX"),
            ModifiedValue.of(Instant.parse("2021-04-07T15:14:00Z"))
        )
        .published(PublishedValue.of(Instant.parse("2021-04-01T20:15:00Z")))
        .aliases(AliasesValue.of(IdValue.of(IdDatabaseValue.CVE, "2021-29421")))
        .summary(SummaryValue.fromString("XXE in pikepdf"))
        .details(DetailsValue.fromString(
            "models/metadata.py in the pikepdf package 2.8.0 through 2.9.2 for Python allows XXE "
                + "when parsing XMP metadata entries."))
        .references(ReferencesValues.of(ReferenceTypeValue.FIX, ReferenceUrlValue.of(
            new URL("https://github.com/pikepdf/pikepdf/commit/"
                + "3f38f73218e5e782fe411ccbb3b44a793c0b343a"))))
        .affected(
            new AffectedValuesBuilder(
                builder(EcosystemValue.PYPI, NameValue.fromString("pikepdf")).build())
                .ranges(
                    new TypeGitBuilder(RepoValue.of(
                        new URL("https://github.com/pikepdf/pikepdf")),
                        GitEventValues.of(EventSpecifierValue.INTRODUCED, "0"),
                        GitEventValues.of(EventSpecifierValue.FIXED,
                            "3f38f73218e5e782fe411ccbb3b44a793c0b343a")
                    ).build(),
                    new TypeEcosystemBuilder(
                        EcosystemEventValues.of(EventSpecifierValue.INTRODUCED, "2.8.0"),
                        EcosystemEventValues.of(EventSpecifierValue.FIXED, "2.10.0"))
                        .build())
                .versions(VersionsValue.of("2.8.0",
                    "2.8.0.post1",
                    "2.8.0.post2",
                    "2.9.0",
                    "2.9.1",
                    "2.9.2"))
                .ecosystemSpecific(EcosystemSpecificValue.fromString("{\"severity\":\"HIGH\"}"))
                .build())
        .build();
  }

  private static Entry rubyEntry() throws MalformedURLException {
    return Entry.builder(
            IdValue.of(IdDatabaseValue.CVE, "2019-3881"),
            ModifiedValue.of(Instant.parse("2021-05-10T00:00:00Z"))
        )
        .published(PublishedValue.of(Instant.parse("2018-04-23T00:00:00Z")))
        .summary(SummaryValue.fromString("Insecure path handling in Bundler"))
        .details(DetailsValue.fromString(
            "Bundler prior to 2.1.0 uses a predictable path in /tmp/, created with insecure "
                + "permissions as a storage location for gems, if locations under the user's home "
                + "directory are not available. If Bundler is used in a scenario where the user "
                + "does not have a writable home directory, an attacker could place malicious code "
                + "in this directory that would be later loaded and executed."))
        .affected(
            new AffectedValuesBuilder(
                new PackageValueBuilder(EcosystemValue.RUBY_GEMS, NameValue.fromString("bundler"))
                    .build())
                .ranges(
                    new TypeEcosystemBuilder(
                        EcosystemEventValues.of(EventSpecifierValue.INTRODUCED, "1.14.0"),
                        EcosystemEventValues.of(EventSpecifierValue.FIXED, "2.1.0")
                    ).build())
                .versions(VersionsValue.of(
                    "1.14.0",
                    "1.14.1",
                    "1.14.2",
                    "1.14.3",
                    "1.14.4",
                    "1.14.5",
                    "1.14.6",
                    "1.15.0.pre.1",
                    "1.15.0.pre.2",
                    "1.15.0.pre.3",
                    "1.15.0.pre.4",
                    "1.15.0",
                    "1.15.1",
                    "1.15.2",
                    "1.15.3",
                    "1.15.4",
                    "1.16.0.pre.1",
                    "1.16.0.pre.2",
                    "1.16.0.pre.3",
                    "1.16.0",
                    "1.16.1",
                    "1.16.2",
                    "1.16.3",
                    "1.16.4",
                    "1.16.5",
                    "1.16.6",
                    "1.17.0.pre.1",
                    "1.17.0.pre.2",
                    "1.17.0",
                    "1.17.1",
                    "1.17.2",
                    "1.17.3",
                    "2.0.0.pre.1",
                    "2.0.0.pre.2",
                    "2.0.0.pre.3",
                    "2.0.0",
                    "2.0.1",
                    "2.0.2",
                    "2.1.0.pre.1",
                    "2.1.0.pre.2",
                    "2.1.0.pre.3"
                ))
                .build())
        .references(ReferencesValues.of(ReferenceTypeValue.ADVISORY,
            ReferenceUrlValue.of(
                new URL("https://github.com/advisories/GHSA-g98m-96g9-wfjq"))))
        .build();
  }

  private static Entry rustEntry() throws MalformedURLException {
    return Entry.builder(
            IdValue.of(IdDatabaseValue.RUSTSEC, "2019-0033"),
            ModifiedValue.of(Instant.parse("2021-01-04T19:02:00Z"))
        )
        .published(PublishedValue.of(Instant.parse("2019-11-16T00:00:00Z")))
        .aliases(AliasesValue.of(
            IdValue.of(IdDatabaseValue.CVE, "2020-25574"),
            IdValue.of(IdDatabaseValue.CVE, "2019-25008")))
        .summary(SummaryValue.fromString("Integer Overflow in HeaderMap::reserve() can cause "
            + "Denial of Service"))
        .details(DetailsValue.fromString("HeaderMap::reserve() used usize::next_power_of_two() "
            + "to calculate"
            + "\nthe increased capacity. However, next_power_of_two() silently overflows\nto 0 if "
            + "given a sufficently large number in release mode.\n\nIf the map was not empty when "
            + "the overflow happens, the library will invoke self.grow(0)\nand start infinite "
            + "probing. This allows an attacker who controls\nthe argument to reserve() to cause a "
            + "potential denial of service (DoS).\n\nThe flaw was corrected in 0.1.20 release of "
            + "http crate.\n"))
        .references(ReferencesValues.of(ReferenceTypeValue.REPORT,
                ReferenceUrlValue.of(new URL("https://github.com/hyperium/http/issues/352"))),
            ReferencesValues.of(ReferenceTypeValue.ADVISORY, ReferenceUrlValue.of(
                new URL("https://rustsec.org/advisories/RUSTSEC-2019-0033.html"))))
        .affected(
            new AffectedValuesBuilder(
                builder(EcosystemValue.CRATES_IO, NameValue.fromString("http")).build())
                .ranges(
                    new TypeSemVerBuilder(
                        SemVerEventValues.of(EventSpecifierValue.INTRODUCED, "0.0.0-0"),
                        SemVerEventValues.of(EventSpecifierValue.FIXED, "0.1.20")
                    ).build())
                .ecosystemSpecific(EcosystemSpecificValue.fromString("{\"functions\":"
                    + "[\"http::header::HeaderMap::reserve\"],\"keywords\":[\"http\",\""
                    + "integer-overflow\",\"DoS\"],\"categories\":[\"denial-of-service\"],"
                    + "\"severity\":\"HIGH\"}"))
                .build())
        .build();
  }

  @Test
  void testInvalidEmptyFile() {
    assertThatThrownBy(() -> deserialize(EMPTY_FILE))
        .isInstanceOf(OsvParserException.class)
        .hasMessage("deserialization error");
  }

  @Test
  void testInvalidEmptyJson() {
    assertThatThrownBy(() -> deserialize(EMPTY_JSON))
        .isInstanceOf(OsvParserException.class)
        .hasMessage("deserialization error");
  }

  @Test
  void testMinimalDeserialization() throws IOException {
    Entry entry = deserialize(MINIMAL_OSV);
    Entry expected = minimalEntry();
    assertThat(entry).isEqualTo(expected);
  }

  @Test
  void testGoExampleDeserialization() throws IOException {
    Entry entry = deserialize(readJsonFile(GO_EXAMPLE_PATH));
    Entry expected = goEntry();
    assertThat(entry).isEqualTo(expected);
  }

  @Test
  void testGoToolExampleDeserialization() throws IOException {
    Entry entry = deserialize(readJsonFile(GO_TOOL_EXAMPLE_PATH));
    Entry expected = goToolEntry();
    assertThat(entry).isEqualTo(expected);
  }

  @Test
  void testNpmExampleDeserialization() throws IOException {
    Entry entry = deserialize(readJsonFile(NPM_EXAMPLE_PATH));
    Entry expected = npmEntry();
    assertThat(entry).isEqualTo(expected);
  }

  @Test
  void testOsvExampleDeserialization() throws IOException {
    Entry entry = deserialize(readJsonFile(OSV_EXAMPLE_PATH));
    Entry expected = osvEntry();
    assertThat(entry).isEqualTo(expected);
  }

  @Test
  void testPythonExampleDeserialization() throws IOException {
    Entry entry = deserialize(readJsonFile(PYTHON_EXAMPLE_PATH));
    Entry expected = pythonEntry();
    assertThat(entry).isEqualTo(expected);
  }

  @Test
  void testRubyExampleDeserialization() throws IOException {
    Entry entry = deserialize(readJsonFile(RUBY_EXAMPLE_PATH));
    Entry expected = rubyEntry();
    assertThat(entry).isEqualTo(expected);
  }

  @Test
  void testRustExampleDeserialization() throws IOException {
    Entry entry = deserialize(readJsonFile(RUST_EXAMPLE_PATH));
    Entry expected = rustEntry();
    assertThat(entry).isEqualTo(expected);
  }

  private static PackageValueBuilder builder(EcosystemValue ecosystem, NameValue name) {
    return new PackageValueBuilder(ecosystem, name);
  }
}
