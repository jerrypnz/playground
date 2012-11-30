(load-file "./watch_service.clj")
(refer 'watch-service)

(defn print-ev
  [ev ctx]
  (println "[foo]" ev " --> " ctx)
  (println "Parent Dir:" (.getParent ctx)))

(defn print-ev-2
  [ev ctx]
  (println "[bar]" ev " --> " ctx))

(println "Start watching...")

(watch-path "/home/jerry/tmp"
            :create print-ev
            :modify print-ev
            :delete print-ev)

(watch-path "/run/shm"
            :create print-ev-2
            :modify print-ev-2
            :delete print-ev-2)

(Thread/sleep 60000)

(println "Stopping watchers..")
(stop-watchers)
(shutdown-agents)
