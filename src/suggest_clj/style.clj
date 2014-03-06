(ns suggest-clj.style
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :refer [em px]]))

(defstyles screen
  [:body
    {:font-family "sans-serif"
     :font-size (px 16)
     :line-height 1.5}]
  [:#app
    {:margin "100px auto"
     :width "30%"}]
  [:.suggestions
    {:-webkit-padding-start 0
     :margin "-2px 0 0 0"
     :overflow-x "hidden"
     :height "32px"}
    [:li
      {:float "left"
       :margin "0 -2px 0 0"
       :padding "2px 12px"
       :border "2px solid #0074D9"}
      [:&.active
        {:background-color "#7FDBFF"}]]]
  [:textarea
    {:width "100%"
     :height "7em"
     :resize "none"
     :padding ".5em"
     :border "2px solid #7FDBFF"}
    [:&:focus
      {:outline "none"
       :border "2px solid #0074D9"}]])
