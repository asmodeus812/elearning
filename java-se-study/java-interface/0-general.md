## The hierarchy

- `Iterable` → `iterator()`
- `Collection` → `add`, `remove`, `contains`, `size`, `isEmpty`, `clear`, `toArray`, bulk ops
- `List` → indexed order + duplicates
- `Set` → uniqueness (no duplicates by `equals`, *except* sorted sets use comparison)
- `SortedSet` → sorted order views (`first/last`, `headSet/tailSet/subSet`)
- `NavigableSet` → navigation around a key (`lower/floor/ceiling/higher`, polls, descending)
- `Queue` → head/tail queue ops (`offer/poll/peek` + strict `add/remove/element`)
- `Deque` → double-ended queue (first/last variants)
- `Map` (not a `Collection`) → key/value associations
- `SortedMap` / `NavigableMap` → sorted keys + navigation (`lowerEntry`, etc.)

