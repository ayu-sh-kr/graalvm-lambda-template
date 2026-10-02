---
name: svg-api-flow-diagrams
description: Create, revise, and repair polished SVG diagrams for Kotlin application HTTP, service, persistence, and asynchronous flows. Use for feature-owned SVGs under docs domain and feature directories, especially when code must determine real branches, connectors or text need repair, or rendered geometry must be validated.
---

# SVG API Flow Diagrams

Create diagrams from the behavior enforced by the current code. Treat an SVG flow as technical documentation: a clean layout is not useful if it invents a branch, omits an early return, or turns independent event listeners into a sequence.

If the repository already contains a related SVG, inspect it for the local visual baseline. Otherwise use the professional baseline below. Store a feature diagram beside its guide under `docs/<domain>/<feature>/`, normally as `flow.svg`.

## Establish the Behavior First

Trace the named flow before editing the SVG.

1. Read the Spring Cloud Function bean or other public entry point for request fields, response shape, validation, and error mapping.
2. Read the orchestration function or service in execution order. Record every guard, early return, lookup, mutation, and response-producing optional value.
3. Read repository methods and SQL for lookup criteria, empty-result behavior, uniqueness, and write semantics.
4. Search for email, HTTP, event, and other downstream effects. Mark transaction and asynchronous boundaries only when the code actually defines them.
5. Read relevant properties, DTOs, assertions, and tests when they define defaults, error codes, or an otherwise ambiguous branch.
6. Trace function validation, service guards, JDBC calls, and downstream effects in their true execution order. Do not invent controller, security-filter, event, or async boundaries that this backend does not implement.

Use `rg` with endpoint paths, function names, exception codes, event names, and repository method names. Do not infer a branch from a method name alone.

Make a short flow model before drawing:

```text
request → authenticate → guard A?
  yes → error response
  no  → lookup B?
           yes → reuse → enrich → success
           no  → validate C? → search/create → optional result → success or empty success
```

## Choose the Diagram Content

Include only behavior that changes the request result, persisted state, or a meaningful after-effect.

- Show auth-derived values when they replace or constrain client input.
- Show guards in their actual order, including explicit error code or user-visible stop when useful.
- Show an `Optional`/empty lookup as a decision only when it changes the next operation or response.
- Combine implementation-only steps that belong together, such as a SQL candidate search and its persistence, if splitting them hides the domain flow.
- Show no-result success explicitly when code returns an empty collection rather than an error.
- Do not add generic database/network failure branches unless the code gives them distinct behavior worth documenting.
- For event flows, show one event card and fan out to its independent listeners. Do not chain listeners merely because they consume the same event.

### Separate Flow Text from Explanatory Text

Keep cards scannable. Put only information needed to identify the component, operation, decision, or outcome inside the card.

- Use a small chip for the step type, a short title, and at most two concise body lines.
- Keep implementation rationale, rollout notes, invariants, and “why this matters” prose in section captions, side notes, or the footer.
- Use a side card only for a directly involved component or meaningful after-effect. Use unboxed text for commentary that does not receive or produce flow.
- Split long labels deliberately. Never rely on clipping, implicit wrapping, or a smaller font to make prose fit.

## Lay Out the Flow

Use a stable visual grammar rather than fitting every branch into the first empty space.

1. Put the request setup on one top row.
2. Put the primary continuation on one centered vertical spine.
3. Leave deliberate vertical gaps (roughly 60–90px) between spine cards so each arrow has a visible tail before its arrowhead.
4. For a decision with a terminal outcome, send the terminal branch horizontally into a reserved side lane and continue the non-terminal branch downward.
5. Keep a side lane's vertical ranges exclusive. Never place a response card in the same vertical span as an error card or another branch card.
6. Reserve lanes by meaning when possible: errors/stops on the right, reusable/cache-hit branches on the left, and new-result enrichment in a separate lower lane.
7. Let parallel event listeners fan out symmetrically from the event, then give each listener its own vertical sub-flow.
8. Move explanatory notes to a footer or small label. Do not give a secondary implementation detail a large card that competes with the main flow.
9. Put unobtrusive section labels outside cards to divide long flows into phases such as filters, session validation, authorization, and response.
10. Prefer rounded rectangular decision cards over diamonds when the question or supporting context needs more than a few words.

For a decision, prefer this shape:

```text
                    yes ───→ [stop / alternate result]
[condition card]
        │
        no
        ▼
[continuing step]
```

Reverse the label names only when the code requires it; preserve the layout convention of side exit versus downward continuation.

### Professional Layout Baseline

Use these defaults for a single-spine diagram, then enlarge rather than compress when content requires it:

| Element | Baseline |
| --- | --- |
| Canvas | 1600px wide; height derived from content |
| Main lane | `x=550`, `width=500` |
| Left alternate lane | `x≈70`, `width≈370` |
| Right stop lane | `x≈1170`, `width≈360` |
| Card padding | 24px horizontal; 20–24px vertical |
| Card radius | 16–20px |
| Vertical gap | 68–96px between card bounds |
| Type hierarchy | 34px page title; 19px card title; 15px body; 13px note; 11px chip |

Use a restrained system: dark header band, very light neutral canvas, white processing cards, blue entry/output cards, purple decisions, green persisted/session state, and coral stops. Use subtle borders and one soft shadow treatment consistently. “Premium” comes from hierarchy, alignment, and whitespace—not decoration density.

## Preflight the Geometry Before Drawing

Do this before adding SVG elements. It prevents a diagram from becoming a sequence of post-hoc spacing fixes.

1. Write down the canvas size, each lane's bounding box, and every card's `x`, `y`, `width`, and `height` in a small layout table.
2. Allocate card height from the longest wrapped label and body copy first. Keep at least 18px of top and bottom text padding; split a label into explicit `<text>` lines instead of relying on a long single line.
3. Reserve a separate horizontal corridor for every branch connector. A connector may touch only its source and destination boundaries; it must not pass through another card, label, or decision.
4. Check the complete extents before implementation: all cards, branch labels, arrowheads, and the footer must fit inside the `viewBox` with visible outer padding.
5. For asynchronous fan-out, reserve a child grid before adding cards. Give every child task its own column or row and keep its connector inside that cell's corridor.
6. Put the main transaction path and asynchronous work in named lanes. If their timing differs, do not make them appear as one uninterrupted serial spine.
7. Reserve a footer legend when connector style or card color carries meaning. Budget space for visual samples, not merely explanatory prose.
8. Define a text contract for every card before drawing it: chip line, title line(s), body line(s), and the exact baseline of the last line. Increase the card height until the final baseline retains at least 18px bottom padding.
9. Record the source boundary, route, final segment direction, and destination boundary for every connector. The arrowhead must enter the destination from the direction the flow actually travels.
10. Plan joins explicitly. When alternate paths converge, either enter different sides of the result card or join with unmarked segments and draw one final arrow; never stack multiple arrowheads on the same segment.

If the flow cannot pass this table-based preflight at a readable font size, enlarge the canvas or split the diagram; do not shrink text until it fits.

## SVG Construction Rules

- Keep a meaningful `<title>` and `<desc>` synchronized with the rendered behavior.
- Use a `viewBox` large enough for the entire diagram and footer. Check the background, content, and footer use the same height.
- Use `marker-end` arrows with `refX="10"` for a 10px marker so the tip meets the target border instead of disappearing inside it.
- Draw an incoming connector after its destination card when an arrowhead must remain visible; end it exactly at the card boundary.
- Use the same card width and height within a lane. Align card centers on the spine and side-lane grid.
- Label a branch close to its connector, not inside either card. Use concise `yes`/`no` labels.
- Draw the main continuation as a straight vertical centerline. Draw terminal branches as short horizontal exits into the stop lane.
- Route alternate side branches through reserved outer corridors. Make the final segment enter the target horizontally when targeting a card's side; do not end a vertical segment halfway along a side boundary.
- Point arrows from the producing step to the side effect or outcome. If a connector visually points back toward its source, rebuild the path rather than moving the label.
- Use dashed connectors only for genuinely asynchronous/post-commit work; explain that meaning in the legend.
- Keep colors semantic and stable: API/response, processing, saved state/event, and error/stop.
- When the diagram uses semantic line styles or colors, include a visual legend with a rendered sample of every style that affects interpretation: for example a solid arrow, dashed async arrow, normal card swatch, and suspect/error card swatch. Text alone is not a legend.
- Keep the page title and one-sentence summary in a dedicated header band. Use one typography hierarchy throughout; do not vary font sizes card by card to solve fitting problems.
- Use native SVG text with explicit lines. Do not use `foreignObject` or automatic HTML wrapping for repository documentation diagrams.

## Repair an Existing Diagram

When a chart feels crowded or an arrow is hidden, repair the structure rather than nudging coordinates blindly.

1. List every card's bounding box: `x`, `y`, `width`, and `height`.
2. Identify lane conflicts: overlapping boxes, overlapping vertical ranges in one lane, or connectors that pass through a card.
3. Reassign whole branches to a free lane or convert the request path to a vertical spine. Do not stack unrelated branches in the same column.
4. Increase the distance between connected cards before changing arrow styling; tails establish direction.
5. Recheck that decision labels still describe the correct code branch after moving a card.
6. Demote non-flow commentary to the footer if it distracts from the request path.
7. Replace locally nudged connector fragments with one intentional orthogonal route whose last segment points into the destination.
8. If several text elements overflow, rebuild the card hierarchy and lane widths together. Do not repair systemic crowding one label at a time.
9. If the structure is sound but the diagram still feels clumsy, normalize the header, chip sizes, card radii, borders, shadows, typography, and gaps as one visual system.

## Verify

1. Validate syntax with `xmllint --noout <diagram>.svg`.
2. Render the SVG to a bitmap and inspect it visually. XML validity does not detect clipped text, awkward whitespace, reversed arrows, or hidden connector tails.
3. Re-read the source flow against the final diagram: every shown branch must exist, and every branch leading to a different response/state must be represented.
4. Before delivery, perform a geometry audit against the preflight table: verify text baselines stay within their card bounds, card rectangles do not overlap, connector corridors are clear, and no element exceeds the `viewBox`.
5. If styles carry semantic meaning, verify the visual legend contains matching rendered samples and is fully inside the canvas.
6. Inspect both the complete diagram and lower/branch-heavy regions at readable scale. Tall thumbnail renderers may crop the canvas; create a temporary wrapper SVG with a shifted `viewBox` and an `<image href="file:///absolute/path/to/diagram.svg">` when a segmented preview is needed.
7. In the rendered output, check every card's first and last text baseline, every arrowhead at its destination boundary, all branch labels, long horizontal corridors, the final response, footer note, and legend.
8. After any geometry correction, render again. Do not treat the first acceptable preview as final.
9. Preserve unrelated SVGs and existing working-tree changes.
