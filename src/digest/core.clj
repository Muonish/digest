(ns digest.core
  (:use compojure.core)
  (:require [digest.db :as db] 
            [digest.views.view :as view]
            [digest.service.users-service :as user]
            [digest.service.posts-service :as post]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.util.response :refer [redirect]]))

; utils

(defn get-req [route foo]
  (GET route request
       (if (get-in request [:session :admin])
           (foo request)
           (redirect "/error"))))

(defn post-req [route foo]
  (POST route request
        (if (get-in request [:session :admin])
            (do
              (foo request)
              (redirect "/main"))
            (redirect "/error"))))

; routes

(defroutes app-routes
  (GET "/" [] (redirect "/home"))
  (GET "/home" request  (view/render-home-page))
  (GET "/main" request  (view/render-main-page request))

  ; auth

  (GET "/auth" request  (view/render-signin-page {}))
  (POST "/auth" request
    (user/sign-in
      request
      #(view/render-signin-page {:error-message "Unable to find user with this email and password"})
      #(redirect "/main")))

  ; post operations

  (GET "/post/:id" request (view/render-post-page request))

  (get-req "/post/add" #(view/render-post-add-page %))
  (post-req "/post/add" #(post/add-post %))

  (get-req "/post/:id/edit" #(view/render-post-edit-page %))
  (post-req "/post/:id/save" #(post/edit-post %))

  (get-req "/post/:id/delete" (fn [req] (post/delete-post req) (redirect "/main")))

  ; resources

  (route/resources "/")
  (route/not-found "Page not found"))

; handler

(def engine
  (handler/site app-routes))

; init

(defn init
  []
  (post/enable-cache-timer))