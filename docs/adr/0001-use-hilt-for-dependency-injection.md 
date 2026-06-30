# 1. Use Hilt for dependency injection

Date: 2026-06-30
Status: Accepted

## Context

DI was handled manually: container classes (`CookItOfflineDataContainer`,
`DefaultCookItNetworkContainer`) instantiated in the Application, a static
`companion object { appContext }`, and a `CreationExtras`-based ViewModel
factory (`AppViewModelProvider`). Consequences:

- A global static Context, set in `onCreate`, leaked into a ViewModel solely
  to resolve a string resource.
- ViewModels could not be constructed in a unit test without the Application.
- Adding a dependency meant editing the container, the factory, and the
  call site — three places, by hand.

## Decision

Adopt Hilt. Dependencies are provided through modules scoped by concern
(Network, Database, DataStore, Repository). ViewModels are `@HiltViewModel`
and obtained via `hiltViewModel()`.

Hilt over Koin: compile-time graph validation catches missing bindings at
build time rather than at runtime, which matters more here than Koin's
lighter setup, given the goal of testable, verifiable wiring.

## Consequences

- Static `appContext` removed; no ViewModel depends on a global Context.
- ViewModels are now constructor-injected and unit-testable in isolation
  (enables the upcoming repository/ViewModel test work).
- Added build cost: kapt annotation processing (~build time noted).
- The two repositories remain separate; unifying them behind a
  single-source-of-truth is tracked separately.