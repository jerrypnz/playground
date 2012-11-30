(import '(java.util.concurrent Executors
                               TimeUnit))

(defn print-name-job [cordinator index total name times]
  #(loop [i times]
     (when-not (< i 0)
       (dosync
        (when (= (mod @cordinator total) index)
          (do
            (println name)
            (alter cordinator + 1)
            (recur (- times 1))))))))

(defn do-tests [names times]
  (let [name-count (count names)
        pool (Executors/newFixedThreadPool (+ 1 name-count))
        cordinator (ref 0)
        tasks (map-indexed #(print-name-job cordinator % name-count %2 times)
                           names)]
    (.invokeAll pool tasks)
    (.awaitTermination pool 1 TimeUnit/MINUTES)))

(do-tests ["A" "B" "C"] 10)
