(ns digest.dal.posts-provider
   (:require [clojure.java.jdbc :as jdbc]
             [java-jdbc.sql :as sql]
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
    [this params]
    (jdbc/insert! db/db-map :post params))
  
  (update-item 
    [this params]
    (jdbc/update! db/db-map :post params (sql/where {:id (params :id)})))
  
  (delete-item 
    [this params]
    (jdbc/delete! db/db-map :post (sql/where params))))
