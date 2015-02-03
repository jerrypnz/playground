(ns core-logic-playground.core
  (:refer-clojure :exclude [==])
  (:require [clojure.core.logic :refer :all]))

(def flights-database {"CZ323" [["PEK" "PNH"]
                                ["PEK" "CAN"]
                                ["CAN" "PNH"]]
                       "CZ305" [["CAN" "AKL"]
                                ["AKL" "MEL"]
                                ["CAN" "MEL"]]
                       "QF123" [["MEL" "PAR"]
                                ["MEL" "LON"]
                                ["PAR" "LON"]]})

(comment (defn init [vars flights]
           (if (seq vars)
             (all 
              (membero (first vars) (flights-database (first flights)))
              (init (rest vars) (rest flights)))
             succeed))
         every)

(defn init [vars flights]
  (everyg (fn [[var flight]](membero var (flights-database flight)))
          (map vector vars flights)))

(defn connectedo [[var1 var2]]
  (fresh [x y conn]
    (== var1 [x conn])
    (== var2 [conn y])))

(defn infer-orig-dest [flights]
  (let [vars (repeatedly (count flights) lvar)
        connections (partition 2 1 vars)]
    (run* [q]
      (== q vars)
      (init vars flights)
      (everyg connectedo connections))))

; Test
;(infer-orig-dest ["MU123" "MU234" "CA888"])

