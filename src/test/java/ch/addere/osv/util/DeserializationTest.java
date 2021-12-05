package ch.addere.osv.util;

import static java.nio.file.Files.readString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ch.addere.osv.Entry;
import ch.addere.osv.impl.EntryImpl;
import ch.addere.osv.impl.fields.AffectedImpl.AffectedBuilder;
import ch.addere.osv.impl.fields.DetailsImpl;
import ch.addere.osv.impl.fields.IdAggregate;
import ch.addere.osv.impl.fields.IdImpl;
import ch.addere.osv.impl.fields.IdImpl.Database;
import ch.addere.osv.impl.fields.ModifiedImpl;
import ch.addere.osv.impl.fields.PublishedImpl;
import ch.addere.osv.impl.fields.ReferencesImpl;
import ch.addere.osv.impl.fields.SummaryImpl;
import ch.addere.osv.impl.fields.affected.DatabaseSpecificImpl;
import ch.addere.osv.impl.fields.affected.EcosystemSpecificImpl;
import ch.addere.osv.impl.fields.affected.PackageImpl;
import ch.addere.osv.impl.fields.affected.VersionsImpl;
import ch.addere.osv.impl.fields.affected.pckg.Ecosystem;
import ch.addere.osv.impl.fields.affected.pckg.NameImpl;
import ch.addere.osv.impl.fields.affected.ranges.RepoImpl;
import ch.addere.osv.impl.fields.affected.ranges.TypeEcosystemImpl;
import ch.addere.osv.impl.fields.affected.ranges.TypeGitImpl;
import ch.addere.osv.impl.fields.affected.ranges.TypeSemVerImpl;
import ch.addere.osv.impl.fields.affected.ranges.events.EcosystemEvent;
import ch.addere.osv.impl.fields.affected.ranges.events.EventSpecifier;
import ch.addere.osv.impl.fields.affected.ranges.events.GitEvent;
import ch.addere.osv.impl.fields.affected.ranges.events.SemVerEvent;
import ch.addere.osv.impl.fields.references.ReferenceTypeImpl;
import ch.addere.osv.impl.fields.references.ReferenceUrlImpl;
import java.io.IOException;
import java.net.URI;
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
    return EntryImpl.builder(
            IdImpl.of(Database.GO, "2021-99998"),
            ModifiedImpl.of(Instant.parse("2021-03-10T23:20:53Z")))
        .build();
  }

  private static Entry goEntry() {
    return EntryImpl.builder(
            IdImpl.of(Database.GO, "2021-99998"),
            ModifiedImpl.of(Instant.parse("2021-03-10T23:20:53Z")))
        .published(PublishedImpl.of(Instant.parse("2021-01-21T19:15:00Z")))
        .aliases(IdAggregate.of(IdImpl.of(Database.CVE, "2021-3114")))
        .summary(SummaryImpl.of("incorrect P-224 curve operations"))
        .details(DetailsImpl.of(
            "The P224() Curve implementation can in rare circumstances generate incorrect outputs, "
                + "including returning invalid points from ScalarMult.\n\nThe crypto/x509 and "
                + "golang.org/x/crypto/ocsp (but not crypto/tls) packages support P-224 ECDSA "
                + "keys, but they are not supported by publicly trusted certificate authorities. "
                + "No other standard library or golang.org/x/crypto package supports or uses the "
                + "P-224 curve.\n\nThe incorrect output was found by the "
                + "elliptic-curve-differential-fuzzer project running on OSS-Fuzz and reported by "
                + "Philippe Antoine (Catena cyber)."))
        .references(
            ReferencesImpl.of(ReferenceTypeImpl.REPORT,
                ReferenceUrlImpl.of(URI.create("https://golang.org/issue/43786"))),
            ReferencesImpl.of(ReferenceTypeImpl.WEB, ReferenceUrlImpl.of(
                URI.create("https://github.com/catenacyber/elliptic-curve-differential-fuzzer")))
        )
        .affected(new AffectedBuilder(
            PackageImpl.of(Ecosystem.Go, NameImpl.of("crypto/elliptic")))
            .ranges(TypeSemVerImpl.of(
                SemVerEvent.of(EventSpecifier.introduced, "1.0.0"),
                SemVerEvent.of(EventSpecifier.fixed, "1.14.14"),
                SemVerEvent.of(EventSpecifier.introduced, "1.15.0"),
                SemVerEvent.of(EventSpecifier.fixed, "1.15.17")
            ))
            .ecosystemSpecific(EcosystemSpecificImpl.of(
                "{\"functions\":[\"P224\"],\"module\":\"std\",\"severity\":\"HIGH\"}"))
            .build())
        .build();
  }

  private static Entry goToolEntry() {
    return EntryImpl.builder(
            IdImpl.of(Database.GO, "2021-99999"),
            ModifiedImpl.of(Instant.parse("2021-03-10T23:20:53Z"))
        )
        .published(PublishedImpl.of(Instant.parse("2021-01-21T19:15:00Z")))
        .aliases(IdAggregate.of(IdImpl.of(Database.CVE, "2021-3115")))
        .summary(
            SummaryImpl.of(
                "packages using cgo can cause arbitrary code execution at build time"))
        .details(DetailsImpl.of(
            "The go command may execute arbitrary code at build time when cgo is in use on "
                + "Windows. This may occur when running \"go get\", or any other command that "
                + "builds code. Only users who build untrusted code (and don’t execute it) are "
                + "affected.\n\nIn addition to Windows users, this can also affect Unix users who "
                + "have \".\" listed explicitly in their PATH and are running \"go get\" or build "
                + "commands outside of a module or with module mode disabled.\n\nThanks to RyotaK "
                + "(https://twitter.com/ryotkak) for reporting this issue."))
        .references(ReferencesImpl.of(ReferenceTypeImpl.REPORT,
            ReferenceUrlImpl.of(URI.create("https://golang.org/issue/43783"))))
        .affected(new AffectedBuilder(PackageImpl.of(Ecosystem.Go, NameImpl.of("cmd/go")))
            .ranges(TypeSemVerImpl.of(
                SemVerEvent.of(EventSpecifier.introduced, "1.0.0"),
                SemVerEvent.of(EventSpecifier.fixed, "1.14.14"),
                SemVerEvent.of(EventSpecifier.introduced, "1.15.10"),
                SemVerEvent.of(EventSpecifier.fixed, "1.15.17")
            ))
            .ecosystemSpecific(EcosystemSpecificImpl.of(
                "{\"severity\":\"HIGH\"}"))
            .build())
        .build();
  }

  private static Entry npmEntry() {
    return EntryImpl.builder(
            IdImpl.of(Database.GHSA, "r9p9-mrjm-926w"),
            ModifiedImpl.of(Instant.parse("2021-03-10T23:40:39Z"))
        )
        .published(PublishedImpl.of(Instant.parse("2021-03-07T11:27:00Z")))
        .aliases(
            IdAggregate.of(
                IdImpl.of(Database.NPM, "1648"),
                IdImpl.of(Database.CVE, "2020-28498"),
                IdImpl.of(Database.SNYK, "JS-ELLIPTIC-1064899"))
        )
        .related(
            IdAggregate.of(
                IdImpl.of(Database.NPM, "1649"),
                IdImpl.of(Database.SNYK, "JAVA-ORGWEBJARSNPM-1069836"))
        )
        .summary(SummaryImpl.of("Use of a Broken or Risky Cryptographic Algorithm"))
        .details(DetailsImpl.of(
            "elliptic is a Fast elliptic-curve cryptography in a plain javascript implementation."
                + "\n\nAffected versions of this package are vulnerable to Cryptographic Issues "
                + "via the secp256k1 implementation in elliptic/ec/key.js. There is no check to "
                + "confirm that the public key point passed into the derive function actually "
                + "exists on the secp256k1 curve. This results in the potential for the private "
                + "key used in this implementation to be revealed after a number of ECDH "
                + "operations are performed.\n\nRemediation: Upgrade elliptic to version 6.5.4 or "
                + "higher.\n"))
        .references(
            ReferencesImpl.of(ReferenceTypeImpl.ADVISORY,
                ReferenceUrlImpl.of(URI.create("https://www.npmjs.com/advisories/1648"))),
            ReferencesImpl.of(ReferenceTypeImpl.ADVISORY,
                ReferenceUrlImpl.of(URI.create("https://nvd.nist.gov/vuln/detail/CVE-2020-28498"))),
            ReferencesImpl.of(ReferenceTypeImpl.FIX,
                ReferenceUrlImpl.of(
                    URI.create("https://github.com/indutny/elliptic/commit/441b7428"))),
            ReferencesImpl.of(ReferenceTypeImpl.ARTICLE,
                ReferenceUrlImpl.of(URI.create(
                    "https://github.com/christianlundkvist/blog/blob/master/2020_05_26_secp256k1_"
                        + "twist_attacks/secp256k1_twist_attacks.md"))),
            ReferencesImpl.of(ReferenceTypeImpl.ADVISORY,
                ReferenceUrlImpl.of(URI.create("https://snyk.io/vuln/SNYK-JS-ELLIPTIC-1064899"))),
            ReferencesImpl.of(ReferenceTypeImpl.PACKAGE,
                ReferenceUrlImpl.of(URI.create("https://www.npmjs.com/package/elliptic")))
        )
        .affected(
            new AffectedBuilder(PackageImpl.of(Ecosystem.npm, NameImpl.of("elliptic")))
                .ranges(TypeSemVerImpl.of(
                    SemVerEvent.of(EventSpecifier.introduced, "1.15.0"),
                    SemVerEvent.of(EventSpecifier.fixed, "1.15.17"),
                    SemVerEvent.of(EventSpecifier.introduced, "6.5.0"),
                    SemVerEvent.of(EventSpecifier.fixed, "6.5.4")
                ))
                .databaseSpecific(DatabaseSpecificImpl.of(
                    "{\"CWE\":\"CWE-327\",\"CVSS\":{\"Score\":\"6.8\",\"Severity\":\"Medium\","
                        + "\"Code\":\"CVSS:3.1/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:N/A:N\"}}"
                ))
                .build())
        .build();
  }

  private static Entry osvEntry() {
    return EntryImpl.builder(
            IdImpl.of(Database.OSV, "2020-584"),
            ModifiedImpl.of(Instant.parse("2021-03-09T04:49:05.965964Z"))
        )
        .published(PublishedImpl.of(Instant.parse("2020-07-01T00:00:18.401815Z")))
        .summary(SummaryImpl.of("Heap-buffer-overflow in collator_compare_fuzzer.cpp"))
        .details(DetailsImpl.of(
            "OSS-Fuzz report: https://bugs.chromium.org/p/oss-fuzz/issues/detail?id=15499\n\nCrash "
                + "type: Heap-buffer-overflow WRITE 3\nCrash state:\ncollator_compare_fuzzer"
                + ".cpp\n"))
        .references(ReferencesImpl.of(
            ReferenceTypeImpl.REPORT, ReferenceUrlImpl.of(
                URI.create("https://bugs.chromium.org/p/oss-fuzz/issues/detail?id=15499"))
        ))
        .affected(
            new AffectedBuilder(PackageImpl.of(Ecosystem.OSS_FUZZ, NameImpl.of("icu")))
                .ranges(
                    TypeGitImpl.of(RepoImpl.of("https://github.com/unicode-org/icu.git"),
                        GitEvent.of(EventSpecifier.introduced,
                            "6e5755a2a833bc64852eae12967d0a54d7adf629"),
                        GitEvent.of(EventSpecifier.fixed,
                            "c43455749b914feef56b178b256f29b3016146eb")
                    )
                )
                .versions(VersionsImpl.of("aVersion"))
                .ecosystemSpecific(EcosystemSpecificImpl.of("{\"severity\":\"HIGH\"}"))
                .databaseSpecific(DatabaseSpecificImpl.of(
                    "{\"source\":\"https://github.com/google/oss-fuzz-vulns/blob/main/vulns/icu/"
                        + "OSV-2020-584.yaml\"}"))
                .build()
        )
        .build();
  }

  private static Entry pythonEntry() {
    return EntryImpl.builder(
            IdImpl.of(Database.PYSEC, "2021-XXXX"),
            ModifiedImpl.of(Instant.parse("2021-04-07T15:14:00Z"))
        )
        .published(PublishedImpl.of(Instant.parse("2021-04-01T20:15:00Z")))
        .aliases(IdAggregate.of(IdImpl.of(Database.CVE, "2021-29421")))
        .summary(SummaryImpl.of("XXE in pikepdf"))
        .details(DetailsImpl.of(
            "models/metadata.py in the pikepdf package 2.8.0 through 2.9.2 for Python allows XXE "
                + "when parsing XMP metadata entries."))
        .references(ReferencesImpl.of(ReferenceTypeImpl.FIX, ReferenceUrlImpl.of(URI.create(
            "https://github.com/pikepdf/pikepdf/commit/3f38f73218e5e782fe411ccbb3b44a793c0b343a"))))
        .affected(
            new AffectedBuilder(PackageImpl.of(Ecosystem.PYPI, NameImpl.of("pikepdf")))
                .ranges(
                    TypeGitImpl.of(RepoImpl.of("https://github.com/pikepdf/pikepdf"),
                        GitEvent.of(EventSpecifier.introduced, "0"),
                        GitEvent.of(EventSpecifier.fixed,
                            "3f38f73218e5e782fe411ccbb3b44a793c0b343a")
                    ),
                    TypeEcosystemImpl.of(
                        EcosystemEvent.of(EventSpecifier.introduced, "2.8.0"),
                        EcosystemEvent.of(EventSpecifier.fixed, "2.10.0")))
                .versions(VersionsImpl.of("2.8.0",
                    "2.8.0.post1",
                    "2.8.0.post2",
                    "2.9.0",
                    "2.9.1",
                    "2.9.2"))
                .ecosystemSpecific(EcosystemSpecificImpl.of("{\"severity\":\"HIGH\"}"))
                .build())
        .build();
  }

  private static Entry rubyEntry() {
    return EntryImpl.builder(
            IdImpl.of(Database.CVE, "2019-3881"),
            ModifiedImpl.of(Instant.parse("2021-05-10T00:00:00Z"))
        )
        .published(PublishedImpl.of(Instant.parse("2018-04-23T00:00:00Z")))
        .summary(SummaryImpl.of("Insecure path handling in Bundler"))
        .details(DetailsImpl.of(
            "Bundler prior to 2.1.0 uses a predictable path in /tmp/, created with insecure "
                + "permissions as a storage location for gems, if locations under the user's home "
                + "directory are not available. If Bundler is used in a scenario where the user "
                + "does not have a writable home directory, an attacker could place malicious code "
                + "in this directory that would be later loaded and executed."))
        .affected(
            new AffectedBuilder(PackageImpl.of(Ecosystem.RUBY_GEMS, NameImpl.of("bundler")))
                .ranges(
                    TypeEcosystemImpl.of(
                        EcosystemEvent.of(EventSpecifier.introduced, "1.14.0"),
                        EcosystemEvent.of(EventSpecifier.fixed, "2.1.0")
                    ))
                .versions(VersionsImpl.of(
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
        .references(ReferencesImpl.of(ReferenceTypeImpl.ADVISORY,
            ReferenceUrlImpl.of(URI.create("https://github.com/advisories/GHSA-g98m-96g9-wfjq"))))
        .build();
  }

  private static Entry rustEntry() {
    return EntryImpl.builder(
            IdImpl.of(Database.RUSTSEC, "2019-0033"),
            ModifiedImpl.of(Instant.parse("2021-01-04T19:02:00Z"))
        )
        .published(PublishedImpl.of(Instant.parse("2019-11-16T00:00:00Z")))
        .aliases(IdAggregate.of(
            IdImpl.of(Database.CVE, "2020-25574"),
            IdImpl.of(Database.CVE, "2019-25008")))
        .summary(SummaryImpl.of("Integer Overflow in HeaderMap::reserve() can cause Denial "
            + "of Service"))
        .details(DetailsImpl.of("HeaderMap::reserve() used usize::next_power_of_two() to calculate"
            + "\nthe increased capacity. However, next_power_of_two() silently overflows\nto 0 if "
            + "given a sufficently large number in release mode.\n\nIf the map was not empty when "
            + "the overflow happens, the library will invoke self.grow(0)\nand start infinite "
            + "probing. This allows an attacker who controls\nthe argument to reserve() to cause a "
            + "potential denial of service (DoS).\n\nThe flaw was corrected in 0.1.20 release of "
            + "http crate.\n"))
        .references(ReferencesImpl.of(ReferenceTypeImpl.REPORT,
                ReferenceUrlImpl.of(URI.create("https://github.com/hyperium/http/issues/352"))),
            ReferencesImpl.of(ReferenceTypeImpl.ADVISORY, ReferenceUrlImpl.of(
                URI.create("https://rustsec.org/advisories/RUSTSEC-2019-0033.html"))))
        .affected(
            new AffectedBuilder(PackageImpl.of(Ecosystem.CRATES_IO, NameImpl.of("http")))
                .ranges(
                    TypeSemVerImpl.of(
                        SemVerEvent.of(EventSpecifier.introduced, "0.0.0-0"),
                        SemVerEvent.of(EventSpecifier.fixed, "0.1.20")
                    ))
                .ecosystemSpecific(EcosystemSpecificImpl.of("{\"functions\":[\"http::header::"
                    + "HeaderMap::reserve\"],\"keywords\":[\"http\",\"integer-overflow\",\"DoS\"],"
                    + "\"categories\":[\"denial-of-service\"],\"severity\":\"HIGH\"}"))
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
}
