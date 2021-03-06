(ns leiningen.zookem
  (:require [clojure.string :as string]
            [clojure.java.io :as io]
            [leiningen.core.main :as main]
            [zookem.core :as zk]))

(defn zookem
  "Start an embedded Zookeeper instance, run the given task, then stop
  Zookeeper"
  [project & args]
  (zk/with-zk (project :zookeeper)
    (println "zookem: Starting ZooKeeper at" zk/*zk-connect-string*)
    (try
      (if (seq args)
        (main/apply-task (first args) project (rest args))
        (while true (Thread/sleep 5000)))
      (finally
        (println "zookem: Closing ZooKeeper")))))
