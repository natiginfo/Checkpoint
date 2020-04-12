[![CodeFactor](https://www.codefactor.io/repository/github/natiginfo/checkpoint/badge)](https://www.codefactor.io/repository/github/natiginfo/checkpoint)
![Snapshot CI](https://github.com/natiginfo/Checkpoint/workflows/Snapshot%20CI/badge.svg?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.natigbabayev.checkpoint/core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.natigbabayev.checkpoint/core)


# Checkpoint

Multipurpose and customizable validation library

## Getting started

### Download

Maven:
```xml
<dependency>
  <groupId>com.natigbabayev.checkpoint</groupId>
  <artifactId>core</artifactId>
  <version>x.y.z</version>
</dependency>
```

Gradle:
```groovy
implementation 'com.natigbabayev.checkpoint:checkpoint-core:x.y.z'
```

(Please replace `x`, `y` and `z` with the latest version numbers: [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.natigbabayev.checkpoint/core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.natigbabayev.checkpoint/core)
)

Snapshots of the development version are available in [Sonatype's `snapshots` repository][snap].

### Usage

Checkpoint allows you to perform validation checks for given input. [`Checkpoint`][checkpoint] is generic collection of 
[rules with boolean output][default-rule]. You can create new instance of Checkpoint either by using builder or DSL:

#### Builder

```kotlin
fun main() {
    val pinCheckpoint = Checkpoint.Builder<CharSequence>()
        .addRule(
            LengthRangeRule.Builder()
                .minLength(4)
                .maxLength(6)
                .whenInvalid { println("Must contain min. 4, max 6 characters") }
                .build()
        )
        .addRule(
            DefaultRuleBuilder<CharSequence>()
                .isValid { it.contains("@") || it.contains("!") }
                .whenInvalid { println("Must contain at least one of the @ or ! characters.") }
                .build()
        )
        .build()

    pinCheckpoint.canPass("123") // returns false and invokes whenInvalid()
    pinCheckpoint.canPass("1234") // returns false and invokes whenInvalid()
    pinCheckpoint.canPass("1234@6!") // returns false and invokes whenInvalid()
    pinCheckpoint.canPass("1234@6") // returns true
}
```


#### DSL

```kotlin
fun main() {
    val pinCheckpoint = checkpoint<CharSequence> {
        addRule(
            lengthRangeRule(minLength = 4, maxLength = 6) {
                println("Must contain min. 4, max 6 characters")
            }
        )
        addRule {
            isValid { it.contains("@") || it.contains("!") }
            whenInvalid { println("Must contain at least one of the @ or ! characters.") }
        }
    }

    pinCheckpoint.canPass("123") // returns false and invokes whenInvalid()
    pinCheckpoint.canPass("1234") // returns false and invokes whenInvalid()
    pinCheckpoint.canPass("1234@6!") // returns false and invokes whenInvalid()
    pinCheckpoint.canPass("1234@6") // returns true
}
```

### Base classes

Checkpoint has several base classes which can be used for creating custom checkpoints and rules:

  - [`com.natigbabayev.checkpoint.core.Rule`][rule]
  - [`com.natigbabayev.checkpoint.core.DefaultRule`][default-rule]
  

Creating new rule:

```kotlin
class NotEmpty(override val callback: Callback<String>) : DefaultRule<String>() {
    override fun isValid(input: String): Boolean {
        return input.isNotEmpty()
    }
}

fun main() {
    val rule = NotEmpty(
        Rule.Callback { println("Cannot be empty") }
    )
    
    val checkpoint = Checkpoint.Builder()
        .addRule(rule)
        .build()
}
```

## License

```
Copyright 2020 Natig Babayev

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
[snap]: https://oss.sonatype.org/content/repositories/snapshots/
[rule]: https://www.natigbabayev.com/Checkpoint/javadoc/core/com.natigbabayev.checkpoint.core/-rule/index.html
[default-rule]: https://www.natigbabayev.com/Checkpoint/javadoc/core/com.natigbabayev.checkpoint.core/-default-rule/index.html
[checkpoint]: https://www.natigbabayev.com/Checkpoint/javadoc/core/com.natigbabayev.checkpoint.core/-checkpoint/index.html