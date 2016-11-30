(ns digest.dal.posts-provider
   (:require [clojure.java.jdbc :as jdbc]
             [clojure.java.jdbc.sql :as sql]
             [digest.models.post :as post]
             [digest.dal.protocols.common-protocol :as common]
             [digest.db :as db]))


(defrecord posts-provider [] common/common-rep-protocol

   (get-all-items 
    [this]
    (map post/map->post-record
      (jdbc/query db/db-map
        (sql/select * :post))))
                         

  (get-item
    [this params]
    (post/map->post-record
      (first 
        (jdbc/query db/db-map 
          (sql/select * :post (sql/where params))))))
  
  (create-item 
    [this params])
  
  (update-item 
    [this params])
  
  (delete-item 
    [this params]))
