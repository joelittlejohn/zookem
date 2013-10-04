(defproject zookem "0.1.0-SNAPSHOT"
  :description "A helper for running embedded Zookeeper instances for integration testing."
  :url "https://github.com/joelittlejohn/zookem"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.netflix.curator/curator-test "1.3.3"]
                 [avout "0.5.4"]
                 [environ "0.4.0"]])
