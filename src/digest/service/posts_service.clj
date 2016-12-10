(ns digest.service.posts-service
  (:require [digest.dal.posts-cache :as cache])
  (:require [digest.dal.posts-provider :as post]))

(def postdal (post/->posts-provider))

; utils

(def now
  (str (java.sql.Timestamp. (System/currentTimeMillis))))

; impl

(defn enable-cache-timer
  []
  (cache/enable-timer))

(defn get-all
  []
  (sort-by :creation_date #(compare %2 %1) (.get-all-items postdal)))

(defn get-post
  [id]
  (prn @cache/cache)
  (cache/get-stub id))

(defn add-post
  [{{:keys [name description] :as post} :params}]
    (cache/new-stub (assoc post :creation_date now)))

(defn edit-post
  [{{:keys [id name description] :as post} :params}]
  (cache/update-stub post))

(defn delete-post
  [{{id :id} :params}]
  (cache/delete-stub id))
