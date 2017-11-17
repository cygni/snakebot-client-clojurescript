(ns cljs-snake-bot.snake-examples.down_then_left_snake
  (:require [cljs-snake-bot.settings :as s]
            [cljs-snake-bot.utils.map-utils :as mu])
  (:use     [cljs-snake-bot.helpers :only [find-first]]))

; This snake will go down until it cant go further, then it will turn left


(defn is-usable [dir map]
 (mu/able-to-use-dir dir (s/state-get :player-id) map))

(defn get-next-movement [msg]
 (let [map (:map msg)]
   (if (is-usable "DOWN" map)  "DOWN" "LEFT")))
