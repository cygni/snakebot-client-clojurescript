(ns cljs-snake-bot.events
  (:require [cljs.nodejs :as nodejs]
            [cljs-snake-bot.messages :as msgs]
            [cljs-snake-bot.settings :as s]
            [cljs-snake-bot.printer :as p]
            [cljs-snake-bot.snake-examples.edge-snake :as es]))

(defn on-player-registered [msg]
  (s/state-set-many {:player-name (:name msg)
                     :player-id (:receivingPlayerId msg)
                     :player-color (:color msg)})
  (if (= "training" s/game-mode)
      (msgs/get-start-game-message)
      nil))


(defn on-map-updated [msg]
  (msgs/get-move-message (es/get-next-movement msg) (:gameId msg) (:gameTick msg)))

(defn on-game-ended [msg]
  (s/state-set :socket-open false)
  nil)

(defn on-snake-died [msg]
  nil)

(defn on-game-starting [msg]
  (s/state-set-many {:number-of-players (:noofPlayers msg)
                     :game-height (:height msg)
                     :game-width (:width msg)})
  nil)

(defn on-invalid-player-name [msg]
  nil)

(defn on-game-link [msg]
  nil)

(defn on-game-result [msg]
  nil)

(defn on-tournament-ended [msg]
  (s/state-set :socket-open false)
  nil)

(defn on-no-active-tournament [msg]
  (s/state-set :socket-open false)
  nil)
