(ns cluedo.core)

(defn fixed-loop
  [f x]
  (loop [x x]
    (let [new-x (f x)] 
      (if (= new-x x)
        x
        (recur new-x)))))

(def cluedo-components
  {:players #{"Toby" "Jenny" "Titus"}
   :weapons #{:Gun :Pipe :Rope}
   :suspects #{:Green :White :Peacock }
   :rooms #{:Ballroom :Library :Billiards}})


(defn reduce-players
 [cluedo f col]
 (reduce #(f %1 %2 (cluedo %2)) col (get-in cluedo [:cluedo :players])))

(defn update-players
  [cluedo f]
  (reduce-players cluedo 
                  (fn [cluedo player-name player] 
                    (update cluedo player-name f))
          cluedo))

(defn new-cluedo-knowledge
  [cluedo]
  (let [cluedo {:cluedo cluedo}]
    (into cluedo 
          (reduce-players cluedo
                          (fn [m p _] (assoc m p {:cards #{} :doesnt-have #{}}))
                          {}))))

(def new-cluedo (new-cluedo-knowledge cluedo-components))

(defn add-fact 
  [cluedo {:keys [player type value]}]
  (update-in cluedo [player type] #(into % value)))

(defn has-card
  [player & cards]
  {:player player :type :cards :value (into #{} cards)})

(defn guess
 [player & possible-cards]
 {:player player :type :guesses :value #{(into #{} possible-cards)}})

(defn doesnt-have
  [player & cards]
  {:player player :type :doesnt-have :value (into #{} cards)})

(defn narrow-guesses
  [toremove guesses]
  (let [guesses (map (fn [g] 
                       (reduce (fn [guess r] (disj guess r)) 
                               g 
                               toremove)) 
                     guesses)
        newfacts (-> (filter #(>= 1 (count %)) guesses)
                     (#(map (fn [n] (first n)) %)))
        stripped-guesses (filter #(< 1 (count %)) guesses)]
    {:guesses stripped-guesses
     :new-cards newfacts}))

(defn resolve-player
  [player]
  (let [{:keys [guesses new-cards]} (narrow-guesses 
                                     (:doesnt-have player) 
                                     (:guesses player))]
    (-> player
        (update :cards #(into % new-cards))
        (assoc :guesses guesses))))


(defn only-one
  [cluedo {:keys [card holder]}]
  (let [other-players (disj 
                       (reduce-players cluedo (fn [c p n]  (conj c p )) #{})
                       holder)]
    (map
     (fn [p] (doesnt-have p card)) 
     other-players)))

(defn known-cards
  [cluedo]
  (reduce-players 
   cluedo
   (fn [c n p] (into c (mapcat (fn [c] 
                              (only-one cluedo {:holder n :card c})) 
                            (:cards p))))
   []))

(defn apply-rules 
  [cluedo & rules]
  (reduce (fn [c f] (add-fact c f)) cluedo (mapcat #(% cluedo) rules)))

(defn consistency-rules
  [cluedo]
  (-> cluedo 
   (update-players resolve-player)
   (apply-rules known-cards)))

(defn resolve-knowledge
  [cluedo]
  (fixed-loop consistency-rules cluedo))

(defn solved? 
  [game]
  (let [{:keys [weapon room suspect]} (:killer game)]
    (when (and weapon room suspect)
      {:weapon weapon :room room :suspect suspect})))


(solved? (new-cluedo cluedo-components))


(def example-cluedo (resolve-knowledge
                     (-> new-cluedo
                         (add-fact (has-card "Toby" :Billiards :Green))
                         (add-fact (guess "Toby" :Peacock :Hall)))))


example-cluedo
