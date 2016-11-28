(ns digest.dal.posts-provider
   (:require [clojure.java.jdbc :as jdbc]
             [digest.models.post :as post]
             [digest.db :as db]))

;(get-all-items
;    [this]
;    (into [] (jdbc/query db/db-map
;                         ["SELECT id, name, description"]
;                         :row-fn #(post/->post-record
;                                   (:id %1)
;                                   (:name %1)
;                                   (:description %1)]))

