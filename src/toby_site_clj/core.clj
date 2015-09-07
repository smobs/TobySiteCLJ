(ns toby-site-clj.core
  [:require 
   [cluedo.core :as cluedo]
   [clojure.data.json :as json]])

(defn handler
  [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (json/write-str cluedo/new-cluedo)})
