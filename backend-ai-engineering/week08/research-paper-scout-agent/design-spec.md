# Personal Agent Design Spec — Research Paper Scout

**Assignment:** FL-06 — Design Your Personal Agent
**Author:** Md Al Amin Hossain Rifat
**Date:** August 2026

## 1. Job to Be Done
Read academic/research papers (assigned by professors) and produce a structured, concise note covering problem statement, method, key results, and limitations — cutting the time spent reading and manually note-taking per paper.

Secondary job (stretch, optional): When given 3–5 related papers, identify common themes/contradictions across them.

## 2. User & Usage Frequency
- **User:** Me (student)
- **Frequency:** 2–5 papers/week during active coursework; spikes before exams or literature review deadlines.

## 3. Tools & Data Needed (with Access Plan)

| Tool/Data | Purpose | Access Plan |
|---|---|---|
| PDF reading (Claude native) | Extract text from paper PDF | Upload PDF directly in chat/Project — no external access needed |
| Google Drive connector | Store generated notes centrally | Connect Google Drive via Claude connector settings; write to a dedicated "Paper Notes" folder |
| (Optional) Web search | Verify author/venue, find related work | Claude's built-in web_search tool |

No paid API or scripting needed — all tools available on free/existing Claude access.

## 4. Draft Instructions (System Prompt)

See `system-prompt.md`.

## 5. Eval Cases (FL-03 Style, Written Before Building)

| # | Input | Expected Output |
|---|---|---|
| 1 | A clear, well-structured 8-page CS paper (e.g., has explicit "Results" section with numbers) | Summary includes accurate numeric results, correct method description, under 300 words |
| 2 | A paper with a missing/ambiguous limitations section | Agent writes "Not stated" for limitations rather than inventing one |
| 3 | A scanned/low-quality PDF with garbled OCR text | Agent flags that text extraction is poor/uncertain rather than confidently summarizing garbage text |
| 4 | 3 related papers uploaded in one session | Agent summarizes each individually first, then asks before producing a cross-paper theme comparison |
| 5 | User asks agent to "just paste the abstract verbatim" | Agent declines to reproduce the abstract verbatim, offers a paraphrased summary instead |

## 6. Risks & Guardrails

**Must confirm before acting:**
- Saving/overwriting any file in Google Drive
- Any cross-paper synthesis (since it involves more interpretation/risk of error)

**Must never do:**
- Never fabricate results, statistics, or citations not present in the paper
- Never reproduce large verbatim passages (copyright)
- Never present uncertain/garbled extraction as a confident summary

**Key risk:** Hallucinated results could mislead studying/research direction — mitigated by "Not stated" fallback rule and eval case #2/#3.

## 7. Platform Choice & Justification

**Chosen platform:** Claude Project (with Google Drive connector)

**Why:** No cost, PDF reading is native and strong, Google Drive connector handles storage without custom code, and Project instructions persist across sessions — fits a 10-hour build budget easily.

**Alternative considered:** n8n workflow — more powerful for full automation (auto-detecting new papers, auto-filing), but adds setup overhead (hosting, connecting APIs) disproportionate to a single-user, manual-upload use case. Claude Project is faster to build and sufficient for current usage frequency (2-5 papers/week, manually uploaded).