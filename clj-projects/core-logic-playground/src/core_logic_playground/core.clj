(ns core-logic-playground.core
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]))

(def flights-database {"MU123" [["PEK" "CAN"]
                                ["PEK" "WUH"]
                                ["WUH" "CAN"]]
                       "MU234" [["CAN" "BKK"]
                                ["CAN" "SIN"]
                                ["BKK" "SIN"]]
                       "CA888" [["SIN" "LHR"]]})

(defn flighto [vars flights]
  (if (seq vars)
    (all 
     (membero (first vars) (flights-database (first flights)))
     (flighto (rest vars) (rest flights)))
    succeed))

(defn connectedo [[var1 var2]]
  (fresh [x y conn]
    (== var1 [x conn])
    (== var2 [conn y])))

(defn infer-orig-dest [flights]
  (let [vars (repeatedly (count flights) lvar)
        connections (partition 2 1 vars)]
    (run* [q]
      (== q vars)
      (flighto vars flights)
      (everyg connectedo connections))))
