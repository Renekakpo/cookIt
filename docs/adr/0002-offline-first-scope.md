# 2. Offline-first scope: single source of truth for favorites only

Date: 2026-06-30
Status: Accepted

## Context

Not all recipe data needs the same treatment. Favorites are user-owned data that must work
offline. Search results are ephemeral and query-dependent. Random "home"
recipes sit in between. The Spoonacular terms also restrict persistent storage
of their content.

Caching every viewed recipe would need an `isFavorite` flag and a Room migration,
just to store recipes the user won't open again. Not worth it.

## Decision

Apply single-source-of-truth offline support to favorites only. A recipe's
presence in the Room table means it is a favorite. recipeDetail() serves
favorites cache-first with a network refresh; non-favorites are served from
network and never written to Room. No isFavorite flag, no schema migration.

Offline detection is pragmatic (catch IOException, fall back to cache) rather
than a proactive ConnectivityManager monitor - the monitor is tracked
separately and not needed for this behavior.

## Consequences

- Favorites open instantly and work fully offline; the cached `cooked` count
  survives network refreshes.
- The favorites table is never polluted by merely-viewed recipes.
- Search and home remain network-backed; offline there shows a clear message,
  not stale data.
- Caching arbitrary viewed recipes is deliberately out of scope.
