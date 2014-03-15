(ns suggest.core
  (:require [suggest.handler :as handler])
  (:use ring.adapter.jetty)
  (:gen-class))

(defn -main []
  (run-jetty handler/app {:port 3000}))
