(ns zookem.core
  (:require [zookeeper :as zk]
            [zookeeper.data :refer [to-bytes]]
            [environ.core :refer [env]])
  (:import [com.netflix.curator.test TestingServer]))

(def ^:dynamic *zk-port* nil)
(def ^:dynamic *zk-connect-string* nil)
(def ^:dynamic *zk-client* nil)

(defn config
  ([k args]
     (config k args nil))
  ([k args default]
     (or (get args k)
         (env (keyword (str "zookem-" (name k))))
         default)))

(defmacro with-zk
  ([body]
     `(with-zk {} ~body))
  ([args & body]
     `(let [port# (Integer. (config :port ~args -1))
            nodes# (config :nodes ~args)
            zk-server# (TestingServer. port#)]
        (binding [*zk-port* (.getPort zk-server#)
                  *zk-connect-string* (.getConnectString zk-server#)]
          (binding [*zk-client* (zk/connect *zk-connect-string*)]
            (try
              (when nodes#
                (doseq [[path# data#] nodes#]
                  (zk/create-all *zk-client* path# :persistent? true)
                  (if (not (nil? data#))
                    (zk/set-data *zk-client* path# (to-bytes data#) -1))))
              ~@body
              (finally
                (zk/close *zk-client*)
                (.close zk-server#))))))))
