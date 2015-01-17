(defproject genetic1 "0.1.0-SNAPSHOT"
  :description "Clojure kata - Evolutionary genetic algorithm."
  :url "http://silvanv.ch"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot genetic1.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
