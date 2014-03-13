(defproject suggest "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [org.clojure/clojurescript "0.0-2173"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [om "0.5.1"]
                 [garden "1.1.5"]]

  :plugins [[lein-cljsbuild "1.0.2"]
            [lein-ancient "0.5.4"]
            [lein-ring "0.8.10"]
            [lein-garden "0.1.8"]]

  :hooks [leiningen.cljsbuild]

  :source-paths ["src"]
  :resource-paths ["resources"]

  :ring {:handler suggest.handler/app}

  :garden {:builds [{;; Optional name of the build:
                     :id "dev"
                     ;; The var containing your stylesheet:
                     :stylesheet suggest-clj.style/screen
                     ;; Compiler flags passed to `garden.core/css`:
                     :compiler {;; Where to save the file:
                                :output-to "resources/public/screen.css"
                                ;; Compress the output?
                                :pretty-print? true}}]}

  :cljsbuild {
    :builds [{:id "dev"
              :jar true
              :source-paths ["src-cljs"]
              :compiler {
                :output-dir "resources/public/js/"
                :output-to "resources/public/js/suggest.js"
                :optimizations :none
                :source-map true}}]})
