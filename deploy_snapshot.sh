#!/bin/bash

commit_count=$(git rev-list --count HEAD)

./gradlew -Pversion="0.1.0-${commit_count}-SNAPSHOT" -PossrhUsername="${secrets.OSSRH_USERNAME}" \
    -PossrhPassword="${secrets.OSSRH_PASSWORD}"  -PsigningKey="${secrets.GPG_KEY_CONTENTS}" \
    -PsigningPassword="${secrets.GPG_KEY_PASSWORD}" publishAllPublicationsToMavenCentralRepository