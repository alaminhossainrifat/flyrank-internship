# Research Paper Scout 🔎📄

An AI agent that reads uploaded academic papers and produces a structured, concise summary — cutting down the manual reading/note-taking time per paper for coursework and literature reviews.

**Built for:** FlyRank AI Internship — General AI Fluency Track (FL-06 → FL-09)
**Author:** Md. Al Amin Hossain Rifat

🎥 **Demo Video:** [https://youtu.be/uceVhul_02k](https://youtu.be/uceVhul_02k)


---

## 1. What It Does & Who It's For

Research Paper Scout is a **Claude Project** configured to read an uploaded research paper (PDF) and return a fixed-structure summary: Title, Authors, Venue/Year, Problem, Method, Key Results, Limitations, and a blank "Relevance Note" field for the user to fill in.

**Who it's for:** Students (built for personal use) who read 2–5 assigned papers/week during active coursework and need a fast, reliable first-pass summary before deciding whether to read the full paper.

It is explicitly **not** meant to replace close reading — it's a triage/note-taking layer.

---

## 2. Setup (reproducible from scratch)

No coding required. Everything runs on Claude's existing web interface.

1. Go to [claude.ai](https://claude.ai) → **Projects** → **Create Project**. Name it `Research Paper Scout`.
2. Open **Project instructions** and paste the contents of [`system-prompt.md`](./system-prompt.md) from this repo.
3. Go to **Settings → Connectors** and connect **Google Drive**.
   - ⚠️ Known gotcha: after connecting, the connector may show *"This connector has no tools available."* Fix: go to **Connectors → Google Drive → Tool permissions** and set all read/write tools to **"Always allow."**
4. In Google Drive, create a folder named `Paper Notes` (the agent writes here).
5. You're ready — open the Project and upload a paper PDF.

No paid API, no external hosting, no scripting.

---

## 3. Usage Example

**Input:** Upload a paper PDF, then type: `Summarize this paper`

**Output (structure):**
```
Title: ...
Authors: ...
Venue/Year: ...
Problem: (2-3 sentences)
Method: (2-3 sentences)
Key Results: (with numbers if present)
Limitations: (or "Not stated" if absent)
My Relevance Note: (blank — fill in yourself)

Want me to save this to your Paper Notes folder in Drive?
```

The agent always **asks for confirmation before saving** to Drive, and **asks before doing a cross-paper theme comparison** if multiple papers are uploaded in one session.

---

## 4. Architecture

```
        ┌──────────────┐
 User → │  Upload PDF   │
        └──────┬───────┘
               ▼
     ┌───────────────────────┐
     │   Claude Project        │
     │  (system prompt =       │
     │   summary structure +   │
     │   guardrails)            │
     └──────┬─────────┬───────┘
            │         │
   (native PDF     (optional)
    reading)      web_search
            │      (verify venue/
            │       related work)
            ▼
     Structured Summary
      shown to user
            │
    user confirms save?
            │ yes
            ▼
   ┌─────────────────────┐
   │ Google Drive          │
   │ ("Paper Notes" folder)│
   │ via Drive connector    │
   └─────────────────────┘
```

No custom backend, no database — the Claude Project + Drive connector is the entire system.

---

## 5. Eval Results (v2)

Five eval cases were defined in the [design spec](./design-spec.md) before building (FL-06). Results after full testing:

| # | Case | Result | Notes |
|---|------|--------|-------|
| 1 | Clear structured paper → accurate numeric summary, correct structure | ✅ **PASS** | Tested on a 52-page and a 23-page paper (build log, Week 7). No fabricated data. |
| 2 | Paper with no explicit limitations → agent should write "Not stated" | ❌ **FAIL** | Agent inferred plausible-sounding limitations ("no peer review," "no empirical validation") instead of writing "Not stated," on a paper with no author-stated limitations. This is a guardrail violation — the agent guessed instead of admitting absence. |
| 3 | Garbled/scanned PDF → agent should flag uncertain extraction | ⚠️ **Inconclusive** | Tested with a photographed scan of a thesis; despite glare/skew, text remained legible, so the agent produced a confident, accurate summary. The uncertainty-flagging guardrail was never actually triggered — no sufficiently degraded sample was available to test this cleanly. |
| 4 | 3 related papers uploaded together → summarize individually, then ask before cross-theme comparison | ✅ **PASS** | Agent summarized all 3 papers separately in correct structure, then explicitly asked before offering a cross-paper theme comparison, matching spec exactly. |
| 5 | User asks for verbatim abstract → agent should refuse and paraphrase | ✅ **PASS** | Agent declined verbatim reproduction citing copyright, offered a paraphrase instead (build log, Week 7). |

**Score: 3/5 clear pass, 1 fail, 1 inconclusive.**

---

## 6. Limitations

- **"Not stated" guardrail is unreliable (Eval #2 failure).** The agent tends to infer plausible limitations from context rather than admitting the source doesn't state any. Fix for a v3: strengthen the instruction to something stricter, e.g. *"Do not infer or guess limitations even if plausible. Only report limitations the author explicitly states. Otherwise write exactly: Not stated."*
- **Word limit (300 words) not consistently enforced** on data-heavy papers with many quantitative results (noted in Week 7 build log).
- **Extraction-uncertainty flagging is untested against truly degraded input** (Eval #3) — current behavior on mildly imperfect scans is confident and accurate, but true OCR-garbled text has not been tested due to lack of a suitable sample.
- **Single-user tool**, manual PDF upload only — no automatic paper discovery/ingestion.
- **No empirical validation of summary quality** against a human-written gold-standard summary; evals so far are structural/behavioral (does it follow the rules), not a measure of summary *quality*.

---

## Repo Contents

- [`design-spec.md`](./design-spec.md) — FL-06 design spec (job, users, tools, eval cases, guardrails, platform justification)
- [`system-prompt.md`](./system-prompt.md) — the exact system prompt used in the Claude Project
- [`build-log.md`](./build-log.md) — FL-07 build log (setup issues, test runs, deviations from spec)
- `README.md` — this file