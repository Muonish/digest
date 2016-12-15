(ns digest.dal.posts-cache
  (:require [digest.dal.posts-provider :as post]))

(def postdal (post/->posts-provider))

(def cache (atom {}))

(def *timer* (agent nil))

(defn start-timer [ms a]
  (letfn [(tfn [] (future (do (Thread/sleep ms) (reset! a {}) (send *timer* tfn))))]
         (send *timer* tfn)))

(defn enable-timer []
  (start-timer 60000 cache)) ; every 60 sec cache reset

; Cache

(defn new-stub [post]
  (let [id ((first (.create-item postdal post)) :generated_key)]
    (swap! cache assoc id (assoc post :id id))))

(defn get-stub [id]
  (let [cached ((keyword id) @cache)]
    (if (nil? cached)
        (let [from-db (.get-item postdal {:id id})]
          (swap! cache assoc (keyword id) from-db)
          from-db)
        cached)))

(defn update-stub [post]
  (swap! cache assoc (keyword (post :id)) post)
  (.update-item postdal post))

(defn delete-stub [id]
  (swap! cache dissoc (keyword id))
  (.delete-item postdal {:id id}))

