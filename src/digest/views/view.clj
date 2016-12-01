(ns digest.views.view
  (:use hiccup.page
        hiccup.element)
  (:require [digest.views.renderer :as renderer]
            [digest.service.posts-service :as post]))

(defn render-home-page
  []
  (renderer/render "home.html" {:docs "document"}))

(defn render-signin-page
  []
  (renderer/render "auth.html"))

(defn render-post-page
  [{params :params}]
  (renderer/render "post.html" {:post (post/get-post params)}))


(defn render-user-page
  [user]
  (renderer/render "user.html" {:user user}))

(defn render-signup-page
  []
  (renderer/render "signup.html"))

(defn render-main-page
  []
  (renderer/render "main.html" {:posts (post/get-all)}))
