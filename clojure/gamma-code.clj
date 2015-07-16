(defn- log2 [n]
  (int (/ (Math/log n) (Math/log 2))))

(defn gamma-encode [n]
  (let [logn (log2 n)
        prefix (concat (repeat logn 1) [0])
        suffix ]))
