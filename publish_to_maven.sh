#!/bin/bash

commit_count=$(git rev-list --count HEAD)

if latest_tag=$(git describe --abbrev=0 --tags); then
  version="${latest_tag}"
else
  version="0.1.0"
fi

if [ "$1" = "SNAPSHOT" ]; then
  final_version="${version}-${commit_count}-SNAPSHOT"
else
  final_version="${version}"
fi

ossrh_username=$2
ossrh_password=$3
gpg_key_contents=$4
gpg_key_password=$5

./gradlew -Pversion="${final_version}" -PossrhUsername="${ossrh_username}" \
  -PossrhPassword="${ossrh_password}" -PsigningKey="${gpg_key_contents}" \
  -PsigningPassword="${gpg_key_password}" publishAllPublicationsToMavenCentralRepository
