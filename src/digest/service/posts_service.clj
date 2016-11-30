(ns digest.service.posts-service
  (:require [digest.dal.posts-provider :as post]))

(def postdal (post/->posts-provider))

(defn get-all
  []
  (prn (.get-all-items postdal))
  (.get-all-items postdal))
