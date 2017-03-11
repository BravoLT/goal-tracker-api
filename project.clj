(defproject neocons-example "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [compojure "1.6.0-beta3"]
                 [clojurewerkz/neocons "3.1.0"]
                 [ring/ring-json "0.5.0-beta1"]]
  :plugins [[lein-ring "0.11.0"]]
  :ring {:handler neocons-example.handler/app}
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring-mock "0.1.5"]]}})
