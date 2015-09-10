(ns toby-site-clj.core
  [:require 
   [cluedo.core :as cluedo]
   [clojure.data.json :as json]
   [ring.adapter.jetty :as jetty]]
  (:gen-class))

(defn handler
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (json/write-str cluedo/new-cluedo)})

(defn -main 
  [& port]
  (let [port (Integer. (or port (System/getenv "PORT")))]
    (jetty/run-jetty #'handler {:port port })))
