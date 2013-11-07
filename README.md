# zookem

A helper for running embedded Zookeeper instances for integration testing. Based on [the curator-test TestingServer](http://curator.incubator.apache.org/curator-test/).

## Usage

As a test helper (example using [Midje](https://github.com/marick/Midje)), just add `[zooken "0.1.0"]` to your project's dev dependencies then:

```clj
(ns (:require [zookem.core :refer [with-zk
                                   *zk-port* *zk-connect-string* *zk-client*]))

(fact "fn-that-uses-zookeeper can read data from zookeeper"
  (with-zk {:port 2181 ;; optional, default is random
            :nodes {"/some/path/with/data" "data"
                    "/some/path/without/data" nil}}
    *zk-port* => 2181
    *zk-connect-string* => "127.0.0.1:51828"
    (fn-that-uses-zookeeper *zk-client*) => ...)
```

The _with-zk_ macro starts up an embedded instance of Zookeeper for testing, runs the body, then shuts down the Zookeeper instance. Inside the body of the macro, the dynamic vars _\*zk-port\*_, _\*zk-connect-string\*_ and _\*zk-client\*_ will be bound.

As a lein plugin:

```clj
(defproject myproject "0.1.0"
  :plugins [[zookem "0.1.0"]]
  :zookem {:port 2181
           :nodes {"/some/path/with/data" "data"
                   "/some/path/without/data" nil}})
```

    $ lein zookem midje

The _zookem_ task starts an embedded instance of Zookeeper then runs the task given as an argument. Once the downstream task completes, the embedded instance of Zookeeper is terminated.

This library supports [environ](https://github.com/weavejester/environ) configuration, so you can also set the Zookeeper port using the 'env' key in your project.clj:

```clj
(defproject myproject "0.1.0"
  :env {:zookem-port "2181"})
```

or as an environment variable:

```
ZOOKEM_PORT=2181
```

or as a system property:

```
-Dzookem.port=2181
```

## License

Copyright © 2013 Joe Littlejohn

Distributed under the Eclipse Public License, the same as Clojure.
