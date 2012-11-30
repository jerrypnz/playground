(ns url-shorten.views
  (:use [url-shorten.common :only [layout]]
        [noir.core :only [defpage defpartial]]
        [hiccup.form :only [form-to text-field submit-button]]
        [hiccup.element :only [link-to]]))

(defpage "/" []
  (layout
   [:h3 "Welcome to Little URL Shortener"]
   [:div.row-fluid
    [:div.span12
     (form-to {:class "form-inline"}
              [:post "/"]
              [:input {:type "url"
                       :name "url"
                       :class "span8"
                       :place-holder "Please input an URL"}]
              (submit-button {:class "btn btn-success"} "Shorten It!"))]]))

(defpage [:post "/"] {:keys [url]}
  (layout
   [:h3 "Here is your shortened URL"]
   [:p (link-to url url)]))

(defpage "/about" []
  (layout
   [:h3 "Jerry's first Noir project, a small URL shortener."]
   [:p "Clojure is cool, Noir is cool, this is only a small demo
        showing how cool they could be!"]))
