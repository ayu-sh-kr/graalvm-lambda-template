---
name: atomic-domain-commits
description: Create coherent Git commits for Kotlin application, build, infrastructure, tests, and documentation changes.
---

# Atomic Domain Commits

- Inspect staged and unstaged changes before changing the index. Preserve unrelated work.
- Stage the smallest independently understandable unit; keep a behavior and its necessary tests together.
- Separate application/build changes from unrelated infrastructure, skills, or documentation changes.
- Review each staged diff and commit with an action prefix: feat, fix, add, patch, chores, or bump.
- Use matching action prefixes for branches and PR titles. Describe the final behavior and actual validation.
- Do not rewrite history, discard unrelated work, or push unless requested. A request to create a PR authorizes publishing its branch and commits.
