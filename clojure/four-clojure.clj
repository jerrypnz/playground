(def ^:dynamic *enable-debug* false)

(defmacro dbg [label expr]
  `(if *enable-debug*
     (let [v# ~expr]
       (println (str "[" ~label "] DEBUG:") v#)
       v#)
     ~expr))

(defmacro with-debug [& body]
  `(binding [*enable-debug* true]
     ~@body))

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
     is-map    #(= (+ (count %) 1) (count (-> % (conj [:bar 1]) (conj [:bar 2]))))]
    (cond
      (is-map col) :map
      (is-set col) :set
      (is-list col) :list
      (is-vector col) :vector)))

;http://www.4clojure.com/problem/58
(defn my-comp [& fs]
  #(first(reduce (fn [v f] [(apply f v)]) %& (reverse fs))))

;; 73 tic-tac-toe

(defn judge-tic-tac-toe [b]
  (letfn [(cords-seq []
            (concat (map #(map vector (repeat %) (range 3)) (range 3))
                    (map #(map vector (range 3) (repeat %)) (range 3))
                    [[[0 0] [1 1] [2 2]]]
                    [[[0 2] [1 1] [2 0]]]))
          (won? [x]
            (some (fn [cords] (every? #(= x (get-in b %)) cords))
                  (cords-seq)))]
    (cond
     (won? :x) :x
     (won? :o) :o
     true nil)))

(defn tic-tac-toe-tests []
  (map
   judge-tic-tac-toe
   [[[:e :e :e]
     [:e :e :e]
     [:e :e :e]]

    [[:x :e :o]
     [:x :e :e]
     [:x :e :o]]

    [[:e :x :e]
     [:o :o :o]
     [:x :e :x]]

    [[:x :e :o]
     [:x :x :e]
     [:o :x :o]]

    [[:x :e :e]
     [:o :x :e]
     [:o :e :x]]

    [[:x :e :o]
     [:x :o :e]
     [:o :e :x]]

    [[:x :o :x]
     [:x :o :x]
     [:o :x :o]]]))

;; problem 39
;; Better solution: mapcat list
(defn my-interleave [col1 col2]
  (mapcat (fn [v1 v2] [v1 v2]) col1 col2))

;; problem 40
(defn my-interpose [e col]
  (drop-last
   (interleave col (repeat e))))

;; problem 41
;; better solution: keep-indexed
(defn drop-every-nth [col n]
  (->> col
       (map-indexed #(if (= (mod (inc %1) n) 0)
                       nil
                       %2))
       (filter identity)))

;; problem 43
;; Better solution: #(apply map list (partition %2 %))
(defn reverse-interleave [col n]
  (map #(take-nth n (drop % col)) (range n)))

;; problem 49
(defn split-seq [n col]
  [(take n col) (drop n col)])

;; problem 44
(defn my-rotate [n col]
  (let [n (mod n (count col))]
    (concat (drop n col) (take n col))))

;; problem 46
(defn flipping-out [f]
  (fn [& args] (apply f (reverse args))))

;; problem 83
(defn half-truth [& args]
  (if (and (some identity args)
           (some not args))
    true false))

;; problem 99
(defn prod-digits [n1 n2]
  (loop [n (* n1 n2)
         col '()]
    (if (> n 0)
      (recur (quot n 10)
             (conj col (mod n 10)))
      col)))

;; problem 122
;; #(Integer/parseInt % 2)
(defn read-bin [s]
  (reduce #(+ (* 2 %1) (- (int %2) (int \0))) 0 s))

;; problem 135
(defn infix-calc [& exprs]
  (reduce (fn [v [f n]] (f v n))
          (first exprs)
          (partition 2 (rest exprs))))

;; problem 97
(defn pascal-triangle [n]
  (loop [col [1]
         n n]
    (if (= n 1)
      col
      (recur (concat [1]
                     (map #(apply + %)
                          (partition 2 1 col))
                     [1])
             (dec n)))))

;; problem 95
(defn tree? [seq]
  (and
   (coll? seq)
   (= (count seq) 3)
   (every? #(or (nil? %) (tree? %))
           (rest seq))))

;; problem 120
(defn count-smaller-roots [col]
  (count (filter (fn [n]
                   (< n
                      (reduce #(+ %1 (* %2 %2)) 0
                              (map #(- (int %) (int \0)) (str n)))))
                 col)))

(defn digits-square-root-sum [n]
  (loop [sum 0 n n]
    (if (> n 0)
      (let [x (mod n 10)
            y (quot n 10)]
        (recur (+ sum (* x x)) y))
      sum)))

;; problem 118
(defn my-map [f col]
  (lazy-seq
   (if (empty? col)
     nil
     (cons (f (first col))
           (my-map f (rest col))))))

;; problem 78
(defn my-trampoline [f & args]
  (loop [f (apply f args)]
    (if (fn? f)
      (recur (f))
      f)))

;; problem 128
(defn read-card [[s r]]
  {:suit ({\D :diamond \H :heart \C :club \S :spade} s)
   :rank (.indexOf "23456789TJQKA" (int r))})

;; problem 100
(comment (defn lcm [& args]
           "Not working...."
           (loop [seqs (apply sorted-set-by
                              #(< (first %) (first %2))
                              (map #(iterate (partial + %) %) args))]
             (let [lowest (first seqs)]
               (if (apply = (dbg "Values" (map first seqs)))
                 (first lowest)
                 (recur (conj (disj seqs lowest) (next lowest))))))))

(defn lcm [& args]
  (letfn [(gcd [x y]
            (loop [[a b] (if (> x y) [x y] [y x])]
              (if (= b 0) a (recur [b (mod a b)]))))]
    (/ (apply * args)
       (reduce gcd args))))

;; problem 96
(defn is-symmetry [[_ l r]]
  (letfn [(trav [t l?]
            (if (not (coll? t))
              [t]
              (let [[e & es] t]
                (cons e (mapcat #(trav % l?) (if l? es (reverse es)))))))]
    (= (trav l true)
       (trav r false))))

;; problem 147
;; Should use +' (note the single quote) to avoid overflow error
(defn lazy-trape [col]
  (lazy-seq
   (let [nxt (map #(apply +' %)
                  (partition 2 1 (concat [0] col [0])))]
     (cons col (lazy-trape nxt)))))

(defn lazy-trape-2 [col]
  (iterate
   (fn [col]
     (map #(apply +' %) (partition 2 1 (concat [0] col [0]))))
   col))

;; problem 146
;; Should use nested for
(defn into-table [m]
  (into {} (mapcat (fn [[k v]] (for [[k1 v1] v] [[k k1] v1])) m)))

;; problem 153
(defn pairwise-sets [s]
  (= (apply + (map count s))
     (count (set (mapcat identity s)))))

;; problem 50
(defn split-by-type [c]
  (map second (group-by type c)))

;; problem 55
(defn count-occur [c]
  (into {} (for [[k v] (group-by identity c)] [k (count v)])))

;; problem 56
(defn my-distinct [c]
  (let [s (atom #{})]
    (remove #(if (@s %)
               true
               (do (swap! s conj %) false))
            c)))

;; problem 59
(defn my-juxt [& fns]
  #(map (fn [f] (apply f %&)) fns))

;; problem 60
(defn red
  ([f n c]
     (map first
          (take-while identity
                      (iterate
                       (fn [[n c]] (when c [(f n (first c)) (next c)])) [n c]))))
  ([f c] (red f (first c) (rest c))))


;; problem 80
;; feeling kinda like cheating
(defn perfect? [n]
  (contains? #{6 28 496 8128} n))

;; problem 77
(defn anagram [words]
  (->> words
       (group-by sort)
       (map (comp set second))
       (remove #(< (count %) 2))
       set))

;; problem 102
(defn camel [w]
  (let [ws (seq (.split w "-"))]
    (apply str (first ws)
           (->> (rest ws)
                (map #(str (.toUpperCase (.substring % 0 1))
                           (.substring % 1)))))))


;; problem 164
(defn run-dfa [{:keys [start accepts transitions]}]
  (letfn [(next-states [[s o]]
            (->> (transitions s)
                 ;; Try accepted states first
                 (sort-by #(not (accepts (second %))))
                 (map (fn [[c s1]] [s1 (conj o c)]))))]
    (->> (tree-seq seq next-states [start []])
         (filter (fn [[s _]] (accepts s)))
         (map #(apply str (second %))))))

(comment
  (let [res (take 2000 (run-dfa '{:states #{q0 q1}
                                  :alphabet #{0 1}
                                  :start q0
                                  :accepts #{q0}
                                  :transitions {q0 {0 q0, 1 q1}
                                                q1 {0 q1, 1 q0}}}))]
    (and (every? (partial re-matches #"0*(?:10*10*)*") res)
         (= res (distinct res))))


  (let [res (take 2000 (run-dfa '{:states #{q0 q1}
                                  :alphabet #{n m}
                                  :start q0
                                  :accepts #{q1}
                                  :transitions {q0 {n q0, m q1}}}))]
    (and (every? (partial re-matches #"n*m") res)
         (= res (distinct res))))


  (let [res (take 2000 (run-dfa '{:states #{q0 q1 q2 q3 q4 q5 q6 q7 q8 q9}
                                  :alphabet #{i l o m p t}
                                  :start q0
                                  :accepts #{q5 q8}
                                  :transitions {q0 {l q1}
                                                q1 {i q2, o q6}
                                                q2 {m q3}
                                                q3 {i q4}
                                                q4 {t q5}
                                                q6 {o q7}
                                                q7 {p q8}
                                                q8 {l q9}
                                                q9 {o q6}}}))]
    (and (every? (partial re-matches #"limit|(?:loop)+") res)
         (= res (distinct res)))))


;; Problem 108
#(loop [coll %&]
  (let [[[x & xs] & xss] (sort-by first coll)]
    (if (= x (or (first (last xss)) x))
      x
      (recur (cons xs xss)))))


;; Problem 117
(defn for-science [m]
  (let [m (mapv vec m)]
    (loop [[[x y :as p] & ps :as s]
           (->> m
                (map-indexed (fn [x i] [x (.indexOf i \M)]))
                (filter (fn [[_ y]] (>= y 0))))

           v #{}]
      (cond
        (empty? s)
        false

        (= \C (get-in m p))
        true

        :else
        (recur (concat
                (->> [[1 0] [-1 0] [0 -1] [0 1]]
                     (map (fn [[x1 y1]] [(+ x x1) (+ y y1)]))
                     (remove v)
                     (filter #(#{\C \space} (get-in m %))))
                ps)
               (conj v p))))))


;; Problem 178
(defn recognize-cards [cards]
  nil)

;; problem 105
(defn keys-vals
  [col]
  (->> (interpose nil col)
       (partition-by keyword?)
       (partition 2)
       (map (fn [[k v]] [(first k) (filter identity v)]))
       (into {})))

;; problem 112
(defn sequs-horris
  [n col sum]
  nil)

;; problem 121
(defn compute-engine [e]
  (fn [m]
    (eval
     `(let [~@(flatten (seq m))]
        ~e)))) ; apparently `eval` is evil :-(

;; problem 79
(defn minimal-path [t]
  (let [t (vec t)
        f (fn [w p] [(+ w (get-in t p)) p])]
    (loop [q (sorted-set-by (fn [[w1 _] [w2 _]] (< w1 w2)) (f 0 [0 0]))]
      (let [[w [x y] :as p1] (first q)]
        (if (= x (dec (count t)))
          w
          (recur (into (disj q p1) (map (partial f w)
                                        [[(inc x) y]
                                         [(inc x) (inc y)]]))))))))

;; problem 84
(defn transitive-closure [c]
  (loop [c1 c
         res c]
    (if (empty c1)
      res
      (let [c] (for [[x1 x2] c
                    [y1 y2] c1
                    :when (= x2 y1)]
                [x1 y2]))))) ; Wrong !
