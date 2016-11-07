(ns digest.db
  (:require [monger.core :as mg]))

(def conn (mg/connect))

(def db (mg/get-db conn "monger-test"))
