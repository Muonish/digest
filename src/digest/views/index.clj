(ns digest.views.index
  (:require [net.cgrand.enlive-html :as enlive]
            [noir.session :as session]))

(enlive/deftemplate index-template "digest/views/index.html"
  []
  [:title] (enlive/content "Digest"))

(defn display "Display index view"
  []
  (index-template))

