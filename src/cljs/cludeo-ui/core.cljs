(ns cluedo-ui.core
  (:require [reagent.core :as r]
            [cluedo.core :as c]))

(defonce game-state (r/atom c/example-cluedo))
(defn player-widget
  [player player-state] 
  [:div  
   [:h3 player]
   [:ul
    [:b "Has cards"]
    (for [card (:cards player-state)]
      [:li (str card)])]])

(defn players-widget
  []
  [:div
   (let [game-state @game-state
         players (get-in game-state [:cluedo :players])]
     (for [player players]
       (let [player-state (get game-state player)]
         ^{:key player} 
         [player-widget player player-state])))]
)
(defn add-guess
  []
[:div
 [:input {:type "button" :value "Guess"}]])

(defn ui
  []
  [:div
   [add-guess]
   [players-widget]])



