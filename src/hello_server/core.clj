(ns hello-server.core
  (:require [org.httpkit.server :as s])
  (:gen-class))

(defn app 
[req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello Clojure!"})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (s/run-server app {:port 8080}))
