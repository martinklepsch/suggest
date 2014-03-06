(ns suggest-cljs.core
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
        (om/set-state! owner :suggest cur-word)
        (om/set-state! owner :text content)))))


(defn handle-tab [e owner state]
  (when (= (.-keyCode e) 9)
    (.preventDefault e))

  (when (and (= (.-keyCode e) 9) (not (nil? (:suggest state))))
    (let [content  (.. e -target -value)
          cursor   (.. e -target -selectionStart)
          left-of-cursor (subs content 0 cursor)
          new-index (mod (inc (:active-idx state)) (count (:suggestions state)))
          next-suggestion (get (:suggestions state) new-index)
          left-after-subs (str (string/replace left-of-cursor #"@\w*$" next-suggestion) " ")
          new-cursor (count left-after-subs)
          right-of-cursor (subs content cursor (count content))
          cur-word (re-find #"@\w*$" left-of-cursor)
          new-value (string/trim (str left-after-subs right-of-cursor))]

      (when (not= content new-value)
        (om/set-state! owner :text new-value)
        (om/set-state! owner :active-idx new-index)
        (set-cursor (.-target e) new-cursor)))))

(defn suggest-area [app owner]
  (reify
    om/IInitState
    (init-state [_]
      {:suggest nil
       :text    ""
       :offset 0
       :active-idx -1})
    om/IDidUpdate
    (did-update [_ _ _]
      (when (< -1 (om/get-state owner :active-idx))
        (let [active-el (om/get-node owner "active-suggestion")
              offset    (.-offsetLeft active-el)]
          (om/set-state! owner :offset offset))))

    om/IWillUpdate
    (will-update [_ props next-state]
      (cond
        (nil? (:suggest next-state))
        (do
          (om/set-state! owner :suggestions [])
          (om/set-state! owner :active-idx -1))
        :else
        (om/set-state! owner :suggestions
                             (vec (filter #(starts-with? % (:suggest next-state))
                                           (map #(str "@" %) (:listing props)))))))

    om/IRenderState
    (render-state [this state]
      (dom/div #js {:className "suggest"}
        (dom/textarea #js {:onChange #(handle-change % owner state)
                           :onKeyDown #(handle-tab % owner state)
;;                            :onBlur #(om/set-state! owner :suggest nil)
                           :value (:text state)} nil)
        (when (:suggestions state)
          (apply dom/ul #js {:className "suggestions"
                             :style #js {:-webkit-transform (str "translateX(-" (:offset state) "px)")}}
             (map-indexed
               (fn [idx, t]
                 (cond
                   (< idx (:active-idx state))
                   (dom/li #js {:className "hidden"} t)
                   (or (= (:suggest state) t) (= idx (:active-idx state)))
                   (dom/li #js {:className "active" :ref "active-suggestion"} t)
                   :else
                   (dom/li nil t)))
               (:suggestions state))))))))

(om/root suggest-area app-state
  {:target (. js/document (getElementById "app"))})
