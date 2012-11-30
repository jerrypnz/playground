;; A test program that tests the memory usage of Clojure maps.

(import '(java.util Random))

(defn build-map 
  "Build a data map"
  [k1-range k2-range]
  (let [random (Random. (System/nanoTime))
        records (for [k1 k1-range
                      k2 k2-range]
                  [{:phone (str (+ 100000000 k1))
                    :category k2}
                   {:total-flow (.nextInt random 100)
                    :total-hits (.nextInt random 10)
                    :total-duration (.nextInt random 100)}])]
    (into {} records)))

(let [base-map (time (build-map (range 2000000)
                                (range 10)))
      addi-map (time (build-map (range 1950000 2050000 10)
                                (range 10)))
      final-map (time (merge-with (partial merge-with +)
                                  base-map
                                  addi-map))]
  (println "Final map count: " (count final-map))
  (print "Press Enter to Finish.")
  (.read (System/in)))