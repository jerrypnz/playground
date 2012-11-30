dynamic_lang(ruby).
dynamic_lang(python).
dynamic_lang(lisp).

static_lang(c).
static_lang(java).
static_lang(clojure).

functional(clojure).
functional(lisp).

oo(ruby).
oo(python).
oo(java).

static_functional(X) :- static_lang(X), functional(X).
static_oo(X) :- static_lang(X), oo(X).
dynamic_functional(X) :- dynamic_lang(X), functional(X).
dynamic_oo(X) :- dynamic_lang(X), oo(X).

