(ns dsl-example.lock
  (:import (java.util.concurrent ConcurrentHashMap)
           (java.util.concurrent.locks ReentrantLock)))

(def ^:private lock-table (ConcurrentHashMap.))

(defn get-lock [key]
  (let [lock (ReentrantLock.)
        existing-lock (.putIfAbsent lock-table key lock)]
    (or existing-lock lock)))

(defn test-lock [key sleep-time]
  (let [lock (get-lock key)]
    (.lock lock)
    (try
      (Thread/sleep sleep-time)
      (println "Hello world")
      (finally
        (.unlock lock)))))

(defmacro with-lock [key & body]
  `(let [lock# (get-lock ~key)]
     (.lock lock#)
     (try
       ~@body
       (finally (.unlock lock#)))))

(defn test-lock-ex [key sleep-time]
  (with-lock key
    (Thread/sleep sleep-time)
    (println "Hello world")))
