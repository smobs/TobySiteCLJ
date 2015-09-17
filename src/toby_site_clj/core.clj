(ns toby-site-clj.core
  [:require 
   [cluedo.core :as cluedo]
   [clojure.data.json :as json]
   [ring.adapter.jetty :as jetty]
   [compojure.core :refer [GET defroutes]]
   [compojure.route :refer [resources]]
   [hiccup.core :refer [html]]
   [hiccup.page :refer [include-js]]]
  (:gen-class))

(defn handler
  [request]
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body (json/write-str cluedo/new-cluedo)})

(defn reagent 
  [request]
  (html [:body 
         [:h1 "Hello"]
         (include-js "js/app.js")]))

(defroutes routes
  (GET "/cluedo/" [] handler) 
  (GET "/" [] reagent)
  (resources "/"))

(defn -main 
  [& port]
  (let [port (Integer. (or port (System/getenv "PORT")))]
    (jetty/run-jetty #'routes {:port port })))

