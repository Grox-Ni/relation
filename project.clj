(defproject relation "0.1.0-SNAPSHOT"
  :description "a relationship network"
  :url "http://lysin.tv/"
  :license {:name "The MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot relation.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
