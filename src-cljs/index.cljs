(ns index
  (:require  [reagent.core :as r]))

(defn some-component 
  []
  [:div 
   [:h1 "Nothing can stop me now!"]
   [:p "I'm king of the world."]])


(def mount-it
  (r/render-component [some-component] 
                      (.-body js/document)))
