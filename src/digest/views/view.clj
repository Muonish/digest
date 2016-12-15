(ns digest.views.view
  (:use hiccup.page
        hiccup.element)
  (:require [digest.views.renderer :as renderer]
            [digest.service.posts-service :as post]))

(defn render-main-page
  [{session :session}]
  (renderer/render "main.html" {:posts (post/get-all) :session session}))

(defn render-user-page
  [user]
  (renderer/render "user.html" {:user user}))

; auth

(defn render-signin-page
  [params]
  (renderer/render "auth.html" params))

(defn render-signup-page
  [params]
  (renderer/render "signup.html" params))

; post

(defn render-post-page
  [{{id :id} :params session :session}]
  (renderer/render "post.html" {:post (post/get-post id) :session session}))

(defn render-post-add-page
  [{session :session}]
  (renderer/render "post-add.html" {:session session}))

(defn render-post-edit-page
  [{{id :id} :params session :session}]
  (prn (post/get-post id))
  (renderer/render "post-edit.html" {:post (post/get-post id) :session session}))

; admin

(defn render-admin-page
  [params]
  (renderer/render "admin.html" params))







