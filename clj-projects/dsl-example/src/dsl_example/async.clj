(ns dsl-example.async
  (:require [clojure.core.async :as async :refer :all]))

(defn sleep [interval]
  (<! (timeout interval)))

(defn producer [id interval]
  (let [c (chan)
        counter (atom 0)]
    (go
     (while true
       (<! (timeout interval))
       (>! c (str id "-" (swap! counter inc)))
       (println (<! c))))
    c))

(defn run-consumer [n cs]
  (go
   (while true
     (let [[v c] (alts! cs)]
       (println "comsuming " v)
       (>! c (str "response " v))))))

(defn test []
  (let [cs [(producer "a" 100)
            (producer "b" 500)
            (producer "c" 1000)]]
    (run-consumer 50 cs)))
