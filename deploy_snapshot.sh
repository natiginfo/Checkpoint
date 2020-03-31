#!/bin/bash

commit_count=$(git rev-list --count HEAD)
ossrh_username=$1
ossrh_password=$2
gpg_key_contents=$3
gpg_key_password=$4

./gradlew -Pversion="0.1.0-${commit_count}-SNAPSHOT" -PossrhUsername="${ossrh_username}" \
    -PossrhPassword="${ossrh_password}"  -PsigningKey="${gpg_key_contents}" \
    -PsigningPassword="${gpg_key_password}" publishAllPublicationsToMavenCentralRepository