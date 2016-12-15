(ns digest.core
  (:use compojure.core)
  (:require [digest.db :as db] 
            [digest.views.view :as view]
            [digest.service.users-service :as user]
            [digest.service.posts-service :as post]
            [digest.service.dsl-service :as dsl]
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
  (GET "/" [] (redirect "/main"))
  (GET "/main" request  (view/render-main-page request))
  (GET "/logout" request (fn [req] (user/logout req #(redirect "/main"))))


  ; dsl

  (get-req "/admin" #(view/render-admin-page %))
  (POST "/admin" request
    (let [res (dsl/gcc (:query (:params request)))]
      ;(prn (:params request))
      ;(prn res)
      (view/render-admin-page (merge request {:error-message res}))))

  ; auth

  (GET "/auth" request  (view/render-signin-page {}))
  (POST "/auth" request
    (user/sign-in
      request
      #(view/render-signin-page {:error-message "Unable to find user with this email and password"})
      #(redirect "/main")))

  ; signup

  (GET "/signup" request  (view/render-signup-page {}))
  (POST "/signup" request
    (user/sign-up
      request
      #(view/render-signup-page {:error-message "Unable to create user with this email and password"})
      #(redirect "/main")))

  ; post operations

  (get-req "/post/add" #(view/render-post-add-page %))
  (post-req "/post/add" #(post/add-post %))

  (get-req "/post/:id/edit" #(view/render-post-edit-page %))
  (post-req "/post/:id/edit" #(post/edit-post %))

  (get-req "/post/:id/delete" (fn [req] (post/delete-post req) (redirect "/main")))

  (GET "/post/:id" request (view/render-post-page request))

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