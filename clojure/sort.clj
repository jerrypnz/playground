(def data
  [{:a 5 :b 5}
   {:a 6 :b 2}
   {:a 2 :b 7}
   {:a 5 :b 2}
   {:a 1 :b 0}
   {:a 5 :b 1}
   {:a 6 :b 1}
   {:a 2 :b 9}])

(println (sort-by #(+ (* -10 (:a %)) (:b %)) data))

(group-by :a data)
