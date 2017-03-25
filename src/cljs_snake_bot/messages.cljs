(ns cljs-snake-bot.messages
  (:require [cljs-snake-bot.constants :as c]
            [cljs-snake-bot.settings :as s]
            [cljs.nodejs :as nodejs])
  (:use     [cljs-snake-bot.helpers :only [find-first]]))

(def os (nodejs/require "os"))

(defn get-start-game-message []
  {:type c/start-game-message})

(defn get-move-message [direction gameId tick]
  {:type c/register-move-message
   :direction (if (nil? direction) "DOWN" direction)
   :gameId gameId
   :gameTick tick})

(defn get-player-registration-message [name]
  {:type c/register-player-message
   :playerName name
   :gameSettings s/default-map})

(defn get-ping-message []
  {:type c/heart-beat-message})

(defn get-client-info-message []
  {:language "Clojurescript"
   :languageVersion "1.7.170"
   :operatingSystem (.platform os)
   :operatingSystemVersion (.release os)
   :clientVersion s/client-version
   :type c/client-info-message})
