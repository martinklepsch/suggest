(ns suggest.core
  (:require [clojure.string :as string]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(def app-state (atom {:listing ["kambash" "jellea" "swannodette" "jensnikolaus" "janl" "jngo" "milesalex"
                                "alper" "soulim" "lucdudler" "juzmcmuz" "mklappstuhl" "se" "jongold"
                                "joecritchley" "nicolesimon" "somewhere" "conordelahunty" "henrikberggren"
                                "espylaub" "gr2m"]}))

(defn starts-with? [string pre]
  (let [m (re-pattern (str "^" pre))]
    (string? (re-find m string))))

(defn set-cursor [el pos]
  (.setSelectionRange el pos))

(defn handle-change [e owner state]
  (let [content  (.. e -target -value)
        cursor   (.. e -target -selectionStart)
        left-of-cursor (subs content 0 cursor)
        cur-word (re-find #"@\w*$" left-of-cursor)]

    (when (not (= (.-keyCode e) 9))
      (do
        (println "handle-change")
        (om/set-state! owner :suggest cur-word)
        (om/set-state! owner :text content)))))


(defn handle-tab [e owner state]
  (when (= (.-keyCode e) 9)
    (let [content  (.. e -target -value)
          cursor   (.. e -target -selectionStart)
          left-of-cursor (subs content 0 cursor)
          left-after-subs (str (string/replace left-of-cursor #"@\w*$" (first (:suggestions state))) " ")
          new-cursor (count left-after-subs)
          right-of-cursor (subs content cursor (count content))
          cur-word (re-find #"@\w*$" left-of-cursor)]

      (.preventDefault e)
      (println state)
      (println new-cursor)

      (om/set-state! owner :text (string/trim (str left-after-subs right-of-cursor)))
      (set-cursor (.-target e) new-cursor))))

(defn suggest-area [app owner]
  (reify
  om/IInitState
  (init-state [_]
    {:suggest nil
     :text    ""})
  om/IWillUpdate
  (will-update [this props next-state]
     (om/set-state! this :suggestions
                    (when-not (nil? (:suggest next-state)) (filter #(starts-with? % (:suggest next-state))
                                                              (map #(str "@" %) (:listing props))))))
  om/IRenderState
  (render-state [this state]
    (dom/div #js {:className "suggest"}
      (dom/textarea #js {:onChange #(handle-change % owner state)
                         :onKeyDown #(handle-tab % owner state)
                         :value (:text state)} nil)
      (when (:suggestions state)
        (apply dom/ul nil
           (map (fn [t] (dom/li nil t)) (:suggestions state))))))))

(om/root suggest-area app-state
  {:target (. js/document (getElementById "app"))})
