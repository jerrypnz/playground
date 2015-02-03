(ns dsl-example.core)

(defn oom []
  (let [[t d] (split-with #(< % 12) (range 1e8))]
        [(count d) (count t)]))

(defn no-oom []
  (let [[t d] (split-with #(< % 12) (range 1e8))]
        [(count t) (count d)]))

