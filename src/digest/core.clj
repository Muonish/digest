(ns digest.core
  (:use compojure.core)
  (:require [digest.db :as db] 
            [digest.views.view :as view]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [ring.util.response :as response]))

(defroutes app-routes
           (GET "/" [] (response/redirect "/home")) 
           (GET "/home" [] (view/render-home-page)) 
           (GET "/auth" [] (view/render-signin-page))
         ;  (POST "/auth" request (post-auth request))
           (GET "/signup" [] (view/render-signup-page
          ; (POST "/signup" request (add-user request))
          ; (GET "/user/:login" [login] (show-user-page login))
           ;(GET "/user/logoff" [] (def logged false)
                                  (response/redirect "/home")))
           (route/resources "/")
           (route/not-found "Page not found"))

(def engine
  (-> (handler/site app-routes) (middleware/wrap-json-body {:keywords? true})))
