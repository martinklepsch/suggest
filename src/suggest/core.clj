(ns suggest.core
  (:require [suggest.handler :as handler])
  (:use ring.adapter.jetty)
  (:gen-class))

(defn -main [port]
    (run-jetty handler/app {:port (Integer. port) :join? false}))
