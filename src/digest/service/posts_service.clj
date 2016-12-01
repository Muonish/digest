(ns digest.service.posts-service
  (:require [digest.dal.posts-provider :as post]))

(def postdal (post/->posts-provider))

(defn get-all
  []
  (.get-all-items postdal))

(defn get-post
  [params]
  (.get-item postdal params))
