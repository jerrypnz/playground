(ns url-shorten.common
  (:use [noir.core :only [defpartial url-for]]
        [hiccup.page :only [include-js include-css html5]]
        [hiccup.element :only [mail-to link-to]]))

(defpartial layout [& content]
  (html5
   [:head
    [:title "Little URL Shortener"]
    (include-css "/css/bootstrap.css")
    (include-css "/css/bootstrap-responsive.css")
    (include-css "/css/docs.css")]
   [:body
    [:div.navbar.navbar-inverse.navbar-fixed-top
     [:div.navbar-inner
      [:div.container
       [:button.btn.btn-navbar {:type "button"
                                :data-toggle "collapse"
                                :data-target ".nav-collapse"}
        [:span.icon-bar]
        [:span.icon-bar]
        [:span.icon-bar]]
       [:a.brand {:href "/"} "Little URL Shortener"]
       [:div.nav-collapse.collapse
        [:ul.nav
         [:li (link-to "/" "Home")]
         [:li (link-to "/about" "About")]]]]]]
    [:div.container
     content]
    [:footer.footer
     [:div.container
      [:p "Powered by Clojure and Noir"]
      [:ul.footer-links
       [:li (link-to "http://clojure.org" "Clojure")]
       [:li (link-to "http://www.webnoir.org/" "Noir")]
       [:li (link-to "http://jerrypeng.me" "Blog")]
       [:li (link-to "https://github.com/moonranger" "Github")]]]]
    (include-js "/js/jquery.js")
    (include-js "/js/bootstrap.js")]))

