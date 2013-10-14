# zookem

A helper for running embedded Zookeeper instances for integration testing.

## Usage

As a test helper:

```clj
(ns (:require [zookem.core :as zookem]))

(fact "fn-that-uses-zookeeper can read data from zookeeper"
  (zookem/with-zk {:port 2181 ;; optional, default is random
                   :nodes {"/some/path/with/data" "data"
                           "/some/path/without/data" nil}}
    zookem/*zk-port* => 2181
    zookem/*zk-connect-string* => "127.0.0.1:51828"
    (fn-that-uses-zookeeper *zk-client*) => ...)
```

As a lein plugin:

```clj
(defproject myproject "0.1.0"
  :plugins [[lein-zookem "0.1.0"]])
```

    $ lein zookem midje

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
