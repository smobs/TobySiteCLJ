(defproject toby-site-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot toby-site-clj.core
  :plugins [[lein-ring "0.9.6"]]
  :profiles {:uberjar {:aot :all}}
  :uberjar-name "toby-site-clj-standalone.jar"
  :clean-targets [:target-path "out"]
  :ring {:handler toby-site-clj.core/handler})
