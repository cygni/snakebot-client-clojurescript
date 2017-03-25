(ns cljs-snake-bot.settings)

(def game-state (atom
                 {:player-name ""
                  :player-color ""
                  :game-height 0
                  :game-width 0
                  :number-of-players 0
                  :socket-open false}))

(def client-version "0.0.1")

(def host-name "localhost")
(def host-port "8080")
(def game-mode "tournament")
(def arena-name nil)

(def map-sizes {:small 0 :medium 1 :large 2})

(def snake-colors (atom ["green" "blue" "cyan" "yellow" "gray"]))

(defn state-get [key]
  (key @game-state))

(defn state-set [key value]
  (if (fn? value)
   (swap! game-state update-in [key] value)
   (swap! game-state assoc key value)))

(defn state-set-many [value-map]
     (reset! game-state (merge @game-state value-map)))

(def printer-settings
   {:pretty-print-map-updated true
    :pretty-print-game-ended true
    :pretty-print-game-starting true
    :pretty-print-snake-died true
    :pretty-print-invalid-player-name true
    :pretty-print-game-link true
    :pretty-print-player-registration true})

(def default-map
  {
   :maxNoofPlayers 5
   :startSnakeLength 1
   :timeInMsPerTick 250
   :obstaclesEnabled true
   :foodEnabled true
   :headToTailConsumes true
   :tailConsumeGrows false
   :addFoodLikelihood 15
   :removeFoodLikelihood 5
   :spontaneousGrowthEveryNWorldTick 3
   :trainingGame false
   :pointsPerLength 1
   :pointsPerFood 2
   :pointsPerCausedDeath 5
   :pointsPerNibble 10
   :noofRoundsTailProtectedAfterNibble 3
   })
