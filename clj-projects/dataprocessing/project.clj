(defproject dataprocessing "0.1.0-SNAPSHOT"
  :description "Single Node map/reduce framework"
  :url "https://github.com/moonranger"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.0-alpha4"]
                 [org.codehaus.jsr166-mirror/jsr166y "1.7.0"]
                 [com.google.guava/guava "13.0"]]
  :aot :all)
