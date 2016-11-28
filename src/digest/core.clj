(ns digest.core
  (:use compojure.core)
  (:require [digest.db :as db]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [ring.util.response :as response]))

(defroutes app-routes
           (GET "/" [] (response/redirect "/home"))
           (route/not-found "Page not found")
           (route/resources "/"))

(def engine
  (-> (handler/site app-routes) (middleware/wrap-json-body {:keywords? true})))
