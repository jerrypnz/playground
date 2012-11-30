(ns dataprocessing.dpi
  (:require [clojure.string :as str])
  (:use dataprocessing.io)
  (:import (java.text SimpleDateFormat)
           (java.io FileReader BufferedReader)))


(defn group-reduce 
  "Group with given group fn and reduce each group with given reduce fn"
  [g f initial-val coll]
  (persistent!
   (reduce (fn [groups elem]
             (let [key (g elem)
                   val (get groups key initial-val)]
               (assoc! groups key (f val elem))))
           (transient {})
           coll)))

(defn parse-dpi-log 
  "Parse a line of DPI log and do data filtering/transforming
   according to the spec"
  [spec line]
  (let [fields (str/split line #"\|")]
    (->> fields
         (map-indexed (fn [i field]
                        (if-let [field-spec (get spec i)]
                          (let [k (first field-spec)
                                v (if-let [transform-fn (second field-spec)]
                                    (transform-fn field)
                                    field)]
                            [k v])
                          nil)))
         (remove nil?)
         (into {}))))

(defn parse-dpi-timestamp 
  "Parse timestamp in DPI data"
  [timestr]
  (let [date-format (SimpleDateFormat. "yyyy/MM/dd HH:mm:ss")]
    (.parse date-format timestr)))

(defn parse-long [str] (Long/parseLong str))

(defn parse-int [str] (Integer/parseInt str))

(def other-dpi-spec
  [[:imsi]
   [:phone-number]
   [:output-octets parse-long]
   [:input-octets parse-long]
   [:area-belong]
   [:area-link]
   [:duration parse-long]
   [:source-ip]
   [:service-op parse-int]
   [:bsid]
   [:flow-type parse-int]
   [:client-type]
   [:category parse-int]
   [:total]
   [:time parse-dpi-timestamp]])


(defn analyze-dpi-log
  "Analyze DPI data"
  [spec reader chunk-size keys vals]
  (let [lines (line-seq reader)
        chunks (partition-all chunk-size lines)]
    (->> chunks
         (pmap (fn [chunk]
                 (->> chunk
                      (map (partial parse-dpi-log spec))
                      (group-reduce #(select-keys % keys)
                                    (fn [result record]
                                      (let [vals (select-keys record vals)]
                                        (if result
                                          (merge-with + result vals)
                                          vals)))
                                    nil))))
         (reduce (partial merge-with (partial merge-with +))))))

(defn analyze-dpi-log-phone-category
  "Analyze DPI log"
  [file-name]
  (with-open [reader (BufferedReader. (FileReader. file-name))]
    (analyze-dpi-log other-dpi-spec
                     reader
                     10000
                     [:phone-number :category]
                     [:output-octets :input-octets])))
