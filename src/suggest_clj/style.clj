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
     :width "30%"
     :overflow "hidden"}]
  [:ul.suggestions
    {:-webkit-padding-start 0
     :position "relative"
     :margin "-2px 0 0 0"
     :border-right "2px solid #0074D9"
     :overflow-x "hidden"
     :width "9999px"
     :transition ".7s"
     :height "32px"}
    [:li
      {:float "left"
       :margin "0 -2px 0 0"
       :padding "2px 12px"
       :border "2px solid #0074D9"}
      [:&.active
        {:transition ".5s"
         :background-color "#7FDBFF"}]
      [:&.hidden
        {:transition ".5s"
         :opacity 0}]]]
  [:textarea
    {:width "100%"
     :height "7em"
     :resize "none"
     :padding ".5em"
     :transition ".5s"
     :border "2px solid #7FDBFF"}
    [:&:focus
      {:outline "none"
       :border "2px solid #0074D9"}]])
