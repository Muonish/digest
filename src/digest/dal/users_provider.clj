(ns digest.dal.users-provider
   (:require [clojure.java.jdbc :as jdbc]
             [java-jdbc.sql :as sql]
             [digest.models.user :as user]
             [digest.dal.protocols.common-protocol :as common]
             [digest.db :as db]))


(defrecord users-provider [] common/common-rep-protocol

  (get-all-items 
    [this]
    (map user/map->user-record
      (jdbc/query db/db-map
        (sql/select * :user))))
                         

  (get-item
    [this params]
    (user/map->user-record
      (first 
        (jdbc/query db/db-map 
          (sql/select * :user (sql/where params))))))
  
  (create-item 
    [this params]
    (jdbc/insert! db/db-map :user params))
  
  (update-item 
    [this params]
    (jdbc/update! db/db-map :user params (sql/where {:id (params :id)})))
    
  
  (delete-item 
    [this params]
    (jdbc/delete! db/db-map :user (sql/where params))))












