(ns digest.core
  (:require [noir.server :as server])
  (:use digest.routes))

(defn -main "Main method"
  [] 
  (server/start 8080))

