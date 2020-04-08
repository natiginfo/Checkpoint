#!/bin/bash

# If last tag is defined, gets commit count since that tag. Otherwise, gets total commit count
if latest_tag="${LATEST_TAG}"; then
  echo "Latest tag is defined"
  commit_count=$(git rev-list --count "${latest_tag}"..HEAD)
else
  echo "Latest tag is not defined"
  commit_count=$(git rev-list --count HEAD)
fi

version="${LATEST_TAG}"

# If it's snapshot build, version will be
if [ "$1" = "SNAPSHOT" ]; then
  final_version="${version}-${commit_count}-SNAPSHOT"
else
  final_version="${version}"
fi

./gradlew -Pversion="${final_version}" -PossrhUsername="${OSSRH_USERNAME}" \
  -PossrhPassword="${OSSRH_PASSWORD}" -PsigningKey="${GPG_KEY_CONTENTS}" \
  -PsigningPassword="${GPG_KEY_PASSWORD}" publishAllPublicationsToMavenCentralRepository
