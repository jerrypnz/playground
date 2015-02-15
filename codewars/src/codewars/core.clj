(ns codewars.core)

(defn digitize [n]
  (map (comp #(- % 48) int) (str n)))

(defn powers [list]
  (if (seq list)
    (inc (* 2 (powers (rest list))))
    0))
