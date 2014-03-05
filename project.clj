(defproject suggest "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2173"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [om "0.5.1"]]

  :plugins [[lein-cljsbuild "1.0.2"]
            [lein-ancient "0.5.4"]]

  :source-paths ["src"]

  :cljsbuild { 
    :builds [{:id "suggest"
              :source-paths ["src"]
              :compiler {
                :output-to "suggest.js"
                :output-dir "out"
                :optimizations :none
                :source-map true}}]})
