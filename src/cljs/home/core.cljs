(ns home.core
  (:require  [reagent.core :as r]
             [cluedo-ui.core :as cl]))

(defn some-component 
  []
  [:div 
   [:h1 "Nothing can stop me now!"]
   [:p "I'm king of the world."]
   [cl/ui]])


(def mount-it
  (r/render-component [some-component] 
                      (.-body js/document)))
