(ns dataprocessing.dpi
  (:require [clojure.string :as str])
  (:use dataprocessing.io))

(defn analyze-dpi-log
  "Parse a nginx log to get IP frequency data"
  [file chunk-size group-by-fn ]
  (let [lines (buffered-line-seq file)
        chunks (partition-all chunk-size lines)
        extract-fn (partial str/split #"|")]
    (->> chunks
         (pmap (fn [chunk]
                 (->> chunk
                      (map (comp first extract-log-fields))
                      frequencies)))
         (reduce (partial merge-with +))
         (sort-by (comp - second)))))
