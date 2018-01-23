# zookem [![Build Status](https://travis-ci.org/joelittlejohn/zookem.png)](https://travis-ci.org/joelittlejohn/zookem)

A helper for running embedded Zookeeper instances for integration testing. Based on [the curator-test TestingServer](http://curator.incubator.apache.org/curator-test/).

## Usage

#### As a test helper 

An example in a `deftest`, just add `[zookem "0.1.2"]` to your project's dev dependencies then:

```clj
(ns (:require [zookem.core :as z))

(deftest fn-that-uses-zookeeper-can-read-data-from-zookeeper
  (z/with-zk {:port 2181 ;; optional, default is random
              :nodes {"/some/path/with/data" "data"
                      "/some/path/without/data" nil}}
    (is (= 2181 z/*zk-port*))
    (is (= "127.0.0.1:2181" z/*zk-connect-string*))
    (is (= <somevalue> (fn-that-uses-zookeeper z/*zk-client*))))
```

The _with-zk_ macro starts up an embedded instance of Zookeeper for testing, runs the body, then shuts down the Zookeeper instance. Inside the body of the macro, the dynamic vars _\*zk-port\*_, _\*zk-connect-string\*_ and _\*zk-client\*_ will be bound.

#### As a lein plugin

```clj
(defproject myproject "0.1.0"
  :plugins [[zookem "0.1.2"]]
  :zookeeper {:port 2181
              :nodes {"/some/path/with/data" "data"
                      "/some/path/without/data" nil}})
```

    $ lein zookem test

The _zookem_ task starts an embedded instance of Zookeeper then runs the task given as an argument. Once the downstream task completes, the embedded instance of Zookeeper is terminated.

This library supports [environ](https://github.com/weavejester/environ) configuration, so you can also set the Zookeeper port using the 'env' key in your project.clj:

```clj
(defproject myproject "0.1.0"
  :env {:zookeeper-port "2181"})
```

or as an environment variable:

```
ZOOKEEPER_PORT=2181
```

or as a system property:

```
-Dzookeeper.port=2181
```

## License

Copyright © 2013 Joe Littlejohn

Distributed under the Eclipse Public License, the same as Clojure.
