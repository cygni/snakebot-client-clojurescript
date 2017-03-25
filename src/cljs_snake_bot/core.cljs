(ns cljs-snake-bot.core
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require [cljs.nodejs :as nodejs]
            [cljs-snake-bot.messagehandler :as mh]
            [cljs-snake-bot.constants :as c]
            [cljs-snake-bot.settings :as s]
            [cljs-snake-bot.messages :as m]
            [cljs-snake-bot.printer :as p]
            [cljs.core.async :as async :refer [<! >! timeout alts! chan close!]]))

(nodejs/enable-util-print!)

(def ws (nodejs/require "ws"))
(def socket (ws (str "ws://" s/host-name ":" s/host-port "/" s/game-mode (when s/arena-name (str "/" s/arena-name)))))

(defn json-str [obj]
  (JSON/stringify (clj->js obj)))

(defn json-parse [j]
  (js->clj (JSON/parse j) :keywordize-keys true))

(defn setup-listener []
    (.on socket "message"
         #(let [response (mh/get-response-message (json-parse %))]
            (when (some? response) (.send socket (json-str response))))))

(def term (chan))

(defn setup-server-ping []
  (go-loop []
    (println "looping")
    (let [to (timeout 5000)]
      (alts! [term to])
      (when (s/state-get :socket-open)
        (do
          (.send socket (json-str (m/get-ping-message)))
          (recur)))
      (async/close! to)
      (async/close! term))))

(defn setup-socket-close []
  (go-loop []
    (when (s/state-get :socket-open)
      (do (<! (timeout 1000))
          (recur)))
    (do (println "Closing socket")
        (.close socket 1000 "Everything worked out fine")
        (s/state-set :socket-open false)
        (>! term "term"))))

(defn setup-socket []
  (.on socket "open"
       #(do (println "socket opened")
            (s/state-set :socket-open true)
            (setup-socket-close)
            (.send socket (json-str (m/get-player-registration-message "emi")))
            (.send socket (json-str (m/get-client-info-message)))
            (setup-listener)
            (setup-server-ping)
            (p/renderer)))
  (.on socket "close"
       #(s/state-set :socket-open false))
  (.on socket "error"
       #(println "socket error: " %1)))

(defn -main []
  (setup-socket))

(set! *main-cli-fn* -main)
