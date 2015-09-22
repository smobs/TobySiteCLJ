(ns home.core
  (:require  [reagent.core :as r]
             [cluedo-ui.core :as cl]))

(defn some-component 
  []
  [cl/ui])


(def mount-it
  (r/render-component [some-component] 
                      (.-body js/document)))
