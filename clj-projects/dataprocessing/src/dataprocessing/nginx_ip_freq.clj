(ns dataprocessing.nginx-ip-freq
  (:require [clojure.core.reducers :as r])
  (:use dataprocessing.io))

(def ^:private nginx-log-regex
  #"^([0-9\.]+) - - \[(.*)\] \"([A-Z]+) (.*) HTTP/(1.[01])\" (\d+) (\d+) \"(.*)\" \"(.+)\" -$")

(defn extract-log-fields 
  "Extract fields from a line of nginx log"
  [line]
  (vec (->> line
            (re-seq nginx-log-regex)
            first
            rest)))

(defn parse-ip-freq 
  "Parse a nginx log to get IP frequency data"
  [file black-list]
  (let [lines (buffered-line-seq file)
        black-list (set black-list)]
    (->> lines
         (map (comp first extract-log-fields))
         (remove #(or (nil? %) (black-list %)))
         frequencies
         (sort-by (comp - second)))))

(defn parse-ip-freq-pmap
  "Parse a nginx log to get IP frequency data"
  [file black-list chunk-size]
  (let [lines (buffered-line-seq file)
        chunks (partition-all chunk-size lines)
        black-list (set black-list)]
    (->> chunks
         (pmap (fn [chunk]
                 (->> chunk
                      (map (comp first extract-log-fields))
                      (remove #(or (nil? %) (black-list %)))
                      frequencies)))
         (reduce (partial merge-with +))
         (sort-by (comp - second)))))

(defn parse-ip-freq-fold
  "Parse a nginx log to get IP frequency data"
  [file black-list chunk-size]
  (let [lines (buffered-line-seq file)
        chunks (partition-all chunk-size lines)
        black-list (set black-list)]
    (->> chunks
         (pmap (fn [chunk]
                (->> (vec chunk) ; vector folding could be boosted by fork/join
                     (r/map (comp first extract-log-fields))
                     (r/remove #(or (nil? %) (black-list %)))
                     (r/fold (fn combile-fn
                               ([] {})
                               ([m1 m2]
                                  (merge-with + m1 m2)))
                             (fn reduce-fn
                               [result ip]
                               (if (contains? result ip)
                                 (update-in result [ip] inc)
                                 (assoc result ip 1)))))))
         (reduce (partial merge-with +))
         (sort-by (comp - second)))))