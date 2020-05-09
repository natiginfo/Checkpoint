# Change Log

## Version 0.5.0 *(2020-05-09)*

* RxJava2 support:
    * Added SingleRule
    * Added SingleRuleBuilder
    * Added SingleCheckpoint (dsl: `singleCheckpoint()`)
    * Added `DefaultRule.canPassSingle(input)` extension function for
    converting result of `DefaultRule.canPass(input)`
    to `Single<Boolean>`

## Version 0.4.0 *(2020-04-24)*

* New rules!
    * Added RegexRule
    * Added ContainsRule
    * Added AnyRule
* Javadoc improvements

## Version 0.3.0 *(2020-04-15)*

* New rules!
    * Added EmailRule
    * Added PaymentCardRule
* README improvements

## Version 0.2.0 *(2020-04-12)*

* New rules!
    * Added ExactLengthRule
    * Added LengthRangeRule
    * Added MaxLengthRule
    * Added MinLengthRule
    * Added NotBlankRule
    * Added NotEmptyRule
* Documentation improvements
* Updated README file with new rules
* Modularization:
    * New artifact: checkpoint-core-abstraction
    * New artifact: checkpoint-core
    * Deprecate core artifact.
* Improve publishing workflow

## Version 0.1.0 *(2020-04-04)*

Initial release.
