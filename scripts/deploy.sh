#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")/.."
command -v aws >/dev/null || { echo "AWS CLI is required." >&2; exit 1; }
command -v docker >/dev/null || { echo "Docker with buildx is required." >&2; exit 1; }
: "${AWS_REGION:?Set AWS_REGION to the ECR and Lambda region}"
stack="${STACK_NAME:-native-lambda}"
platform="${PLATFORM:-linux/amd64}"
case "$platform" in
  linux/amd64) architecture=x86_64 ;;
  linux/arm64) architecture=arm64 ;;
  *) echo "PLATFORM must be linux/amd64 or linux/arm64." >&2; exit 1 ;;
esac
tag="${IMAGE_TAG:-$(git rev-parse --short=12 HEAD)-$(date -u +%Y%m%d%H%M%S)}"
aws cloudformation deploy --region "$AWS_REGION" --stack-name "$stack-ecr" --template-file infra/ecr.yaml
repository="$(aws cloudformation describe-stacks --region "$AWS_REGION" --stack-name "$stack-ecr" \
  --query 'Stacks[0].Outputs[?OutputKey==`RepositoryUri`].OutputValue | [0]' --output text)"
registry="${repository%%/*}"
aws ecr get-login-password --region "$AWS_REGION" | docker login --username AWS --password-stdin "$registry"
docker buildx build --platform "$platform" --provenance=false --push -t "$repository:$tag" .
aws cloudformation deploy --region "$AWS_REGION" --stack-name "$stack" --template-file infra/lambda.yaml \
  --capabilities CAPABILITY_IAM --parameter-overrides "ImageUri=$repository:$tag" "Architecture=$architecture"
aws cloudformation describe-stacks --region "$AWS_REGION" --stack-name "$stack" \
  --query 'Stacks[0].Outputs' --output table
