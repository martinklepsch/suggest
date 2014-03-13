(ns suggest.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [ring.util.response :as res]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] (res/file-response "index.html" {:root "resources/public"}))
  (route/resources "/public")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
