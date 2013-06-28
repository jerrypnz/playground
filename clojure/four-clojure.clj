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
(defn my-comp [func & rest-funcs])

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
