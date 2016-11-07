(ns digest.routes
  (:require [noir.core :as noir]
            [digest.views.index :as index]))

(noir/defpage [:get "/"] []
  (index/display))
