(ns cljs-snake-bot.events-test
  (:require [cljs.test :refer-macros [async deftest is testing]]
            [cljs-snake-bot.settings :as s]
            [cljs-snake-bot.events :as e]))
(def incoming-message {
                       :name "axel"
                       :player-color "green"
                       :receivingPlayerId "123"
                       })


(deftest setting-state
    ( s/state-set-many incoming-message)
    (is (= "green"  (s/state-get :player-color))))

(deftest on-player-registered-check
    ( e/on-player-registered incoming-message)
    (is (= "123"  (s/state-get :player-id))))


