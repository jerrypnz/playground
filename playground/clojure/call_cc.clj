(defn call-cc [f cc]
  (f (fn [x k]
       (cc x))
     cc))

(deftask edit-configuration
  [data]
  (fn [msg session]
    (let [params (convert-param-names data)]
      (doseq [batch (partition-all params 20)]
        (add-object batch (fn [msg session]
                            ))))
    ))