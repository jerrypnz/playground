(ns dsl-example.orm)

(def schema-map {})

(defn handle-relation [schema relation [entity foreign-key]]
  (if (keyword? entity)
    (let [key (or foreign-key
                  (keyword (str (name (if (= :belongs relation)
                                        entity
                                        (:name schema)))
                                "-id")))
          schema (if (= :belongs relation)
                   (update-in schema [:cols] conj key)
                   schema)]
      (update-in schema [:relations] conj [relation [entity key]]))
    (throw (IllegalArgumentException.
            (str "The entity in a relation must be a keyword: " entity)))))

(defn handle-validates [schema [col validator]]
  (assoc-in schema [:validators col] validator))

(defn handle-schema-element [schema elem]
  (if (coll? elem)
    (let [[key & args] elem]
      (case key
        belongs (handle-relation schema :belongs args)
        has-many (handle-relation schema :has-many args)
        validates (handle-validates schema args)
        (throw (IllegalArgumentException.
                (str "Unknown defentity clause: " key)))))
    (if (keyword? elem)
      (update-in schema [:cols] conj elem)
      (throw (IllegalArgumentException.
              (str "Invalid defentity form:" elem))))))

(defmacro defentity [name & body]
  (let [schema (reduce handle-schema-element
                       {:name name
                        :cols []
                        :relations []
                        :validators {}}
                       body)]
    `(alter-var-root #'schema-map assoc ~name ~schema)))

(comment
  (defn save
    ([entity]
       (let [entity-name (:_name entity)]
         (save entity-name entity)))
    ([entity-name entity]
       (if-let [schema (schema-map entity-name)]
         (let [])
         (throw (IllegalArgumentException. (str "Unknown entity: " entity-name)))))))

