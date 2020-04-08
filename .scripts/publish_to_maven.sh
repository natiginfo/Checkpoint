#!/bin/bash

# If last tag is defined, gets commit count since that tag. Otherwise, gets total commit count
if latest_tag=$(git describe --abbrev=0 --tags); then
  commit_count=$(git rev-list --count "${latest_tag}"..HEAD)
else
  commit_count=$(git rev-list --count HEAD)
fi

# Get latest tag and if it's not defined, version will be 0.1.0
if latest_tag=$(git describe --abbrev=0 --tags); then
  version="${latest_tag}"
else
  version="0.1.0"
fi

# If it's snapshot build, version will be
if [ "$1" = "SNAPSHOT" ]; then
  final_version="${version}-${commit_count}-SNAPSHOT"
else
  final_version="${version}"
fi

./gradlew -Pversion="${final_version}" -PossrhUsername="${OSSRH_USERNAME}" \
  -PossrhPassword="${OSSRH_PASSWORD}" -PsigningKey="${GPG_KEY_CONTENTS}" \
  -PsigningPassword="${GPG_KEY_PASSWORD}" publishAllPublicationsToMavenCentralRepository
