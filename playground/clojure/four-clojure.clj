;http://www.4clojure.com/problem/30
(defn remove-dup [col]
  (reduce (fn [res cur]
            (if (= cur (last res))
              res
              (conj res cur)))
          [] col))

(defn remove-dup-2 [col]
  (map first
       (partition-by identity col)))

;http://www.4clojure.com/problem/31
(defn pack-seq [col]
  (rest (first
      (reduce (fn [[res chld] cur]
                (if (= cur (first chld))
                  [res (cons cur chld)]
                  [(conj res chld) [cur]]))
              [[] []]
              col))))

(defn pack-seq-2 [col]
  (partition-by identity col))

;http://www.4clojure.com/problem/33
(defn replic-seq [col n]
  (if (= n 1)
    col
    (apply interleave (repeat n col))))

;http://www.4clojure.com/problem/65
(defn seq-type [col]
  (let
    [is-list   #(= :bar (first (-> % (conj :foo) (conj :bar))))
     is-vector #(= :bar (last  (-> % (conj :foo) (conj :bar))))
     is-set    #(= (+ (count %) 1) (count (-> % (conj :bar) (conj :bar))))
     is-map    #(= (+ (count %) 1) (count (-> % (conj [:bar 1]) (conj [:bar 2]))))
     ]
    (cond
      (is-map col) :map
      (is-set col) :set
      (is-list col) :list
      (is-vector col) :vector)))

;http://www.4clojure.com/problem/58
(defn my-comp [func & rest-funcs])

