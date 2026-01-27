## Locale

The locale represents a specific geographical or political cultural region, an operation that requires a local to
perform its task is called locale-sensitive, and uses the Locale to tailor the information to the user based on the
information in the locale

### Properties

- language - the primary language
- region - the country or region
- script - specific language script
- variant - sub-variant or dialect

### Interface

- `Locale({lang})` - construct a locale based on the language alone, that is the primary property of the local, the
  language is a code based string that represent the language - "en", "fr" etc.
- `Local({lang}, {country})` - construct the local based on the country and the language, like for example "fr" "CA",
  the country codes unlike the language codes are usually upper case
- `Locale Builder` - the local also exposes a builder class that can be used to construct the full components of a
  local including script, variant etc - `Builder().setLanguage("sr").setScript("Latn").setRegion("RS").build();`

## Bundles

## Properties

### Interface
