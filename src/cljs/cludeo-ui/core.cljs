(ns cluedo-ui.core
  (:require [reagent.core :as r]))

(def players [
                  {:name "Toby" :cards ["Peacock" "Library"]}
                  {:name "Jenny"}
                  {:title "Dr Dre"}])

(defonce game-state (r/atom 0))

(defn player-widget
  [player] 
  [:div  [:h3 (get player :name "Mysterious Challenger")]
   [:ul 
    (for [card (:cards player)]
      [:li (str card)]
          )]])

(defn ui
  []
  [:div 
   "Push the button" 
   @game-state
   [:input {:type "button" :value "HELLLO" 
            :on-click #(swap! game-state inc)}]
   
   (for [player players]
     ^{:key player} 
     [player-widget player])])
