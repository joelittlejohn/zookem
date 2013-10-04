(ns zookem.core
  (:require [avout.core :as zk]
            [environ.core :refer [env]])
  (:import [com.netflix.curator.test TestingServer]))

(def ^:dynamic *zk-port* nil)
(def ^:dynamic *zk-connect-string* nil)
(def ^:dynamic *zk-client* nil)

(defn- config
  ([k args]
     (config k args nil))
  ([k args default]
     (or (args k)
         (env (str "zookem-" (name k)))
         default)))

(defmacro with-zk [args & body]
  `(let [args# (apply hash-map ~args)
         port# (config :port args# -1)
         data# (config :data args#)
         zk-server# (TestingServer. port#)]
     (binding [*zk-port* (.getPort zk-server#)
               *zk-connect-string* (.getConnectString zk-server#)]
       (binding [*zk-client* (zk/connect *zk-connect-string*)]
         (try
           (when data#
             (doseq [[path# value#] data#] (zk/zk-atom *zk-client* path# value#)))
           ~@body
           (finally (.close zk-server#)))))))
