(defproject omplete "0.1.0"
  :description "a reusable component for @-triggered autocompletions"
  :url "https://github.com/mklappstuhl/omplete"

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2173"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [om "0.5.1"]
                 [garden "1.1.5"]]

  :plugins [[lein-cljsbuild "1.0.2"]
            [lein-ancient "0.5.4"]
            [lein-garden "0.1.8"]]

  :source-paths ["src"]

  :garden {:builds [{;; Optional name of the build:
                     :id "screen"
                     ;; The var containing your stylesheet:
                     :stylesheet suggest-clj.style/screen
                     ;; Compiler flags passed to `garden.core/css`:
                     :compiler {;; Where to save the file:
                                :output-to "resources/screen.css"
                                ;; Compress the output?
                                :pretty-print? true}}]}

  :cljsbuild {
    :builds [{:id "suggest"
              :source-paths ["src/omplete"]
              :compiler {
                :output-to "omplete.js"
                :output-dir "out"
                :optimizations :none
                :source-map true}}]})
