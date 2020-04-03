# Releasing

Snapshot releases are automatically published when there is new push to master branch.

To make staging release, follow the steps:

 1. Update the `CHANGELOG.md` for the impending release.
 2. Update the `README.md` with the new version.
 3. `git commit -am "Prepare for release X.Y.Z."` (where X.Y.Z is the new version)
 4. `git tag -a X.Y.Z -m "Version X.Y.Z"` (where X.Y.Z is the new version)
 5. `git push && git push --tags`
 6. Visit [Sonatype Nexus](https://oss.sonatype.org/) and promote the artifact.
