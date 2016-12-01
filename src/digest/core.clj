(ns digest.core
  (:use compojure.core)
  (:require [digest.db :as db] 
            [digest.views.view :as view]
            [digest.service.users-service :as user]
            [digest.service.posts-service :as post]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [redirect]]))


(defroutes app-routes
  (GET "/" [] (redirect "/home"))
  (GET "/home" [] (view/render-home-page))
  (GET "/auth" [] (view/render-signin-page))
  (GET "/post/:id" request (view/render-post-page request))
  (GET "/main" [] (view/render-main-page))
  (POST "/auth" request (user/sign-in request #(redirect "/error") #(redirect "/main")))
  (route/resources "/")
  (route/not-found "Page not found"))

(def engine
  (-> (handler/site app-routes) (middleware/wrap-json-body {:keywords? true})))
