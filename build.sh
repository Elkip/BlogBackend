# Build and Package the backend (Ktor) and frontend (Angular)

function buildBackend() {
  ./gradlew clean build tgz
  cp dist/backend.tgz ../dist
}

buildBackend
#scp dist/backend.tgz server:/tmp/