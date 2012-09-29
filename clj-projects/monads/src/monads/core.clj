(ns monads.core)

(defn m-bind-identity 
  "Identify monad"
  [val f]
  (f val))

(defn m-result-identify 
  "Identity monad"
  [val]
  val)

(defn m-bind-seq 
  "sequence monad"
  [val f]
  (mapcat f val))

(defn m-result-seq
  "sequence monad"
  [val]
  [val])

(defn m-bind-maybe 
  "maybe monad"
  [val f]
  (if (nil? val)
    nil
    (f val)))


(defn test-with-let
  []
  (let [a 3
        b (* a 4)
        c (+ b 10)]
    (* a b c)))


(defn test-with-monad
  [data m-bind m-result]
  (m-bind data
          (fn [a]
            (m-bind (* a 4)
                    (fn [b]
                      (m-bind (+ b 10)
                              (fn [c]
                                (m-result (* a b c)))))))))
