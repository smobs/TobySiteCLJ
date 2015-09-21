(defproject toby-site-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring/ring-jetty-adapter "1.3.0"]
                 [org.clojure/data.json "0.2.6"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/clojurescript "1.7.48" :scope "provided"]
                 [reagent "0.5.1"]]

  :min-lein-version "2.5.0"
  :main ^:skip-aot toby-site-clj.core
  :source-paths ["src/clj"]
  :plugins [[lein-ring "0.9.6"]
            [lein-cljsbuild "1.1.0"]]
  :profiles {:uberjar {:aot :all}}
  :hooks [leiningen.cljsbuild] 
  :uberjar-name "toby-site-clj-standalone.jar"
  :clean-targets ^{:protect false} [:target-path 
                  [:cljsbuild :builds :app :compiler :output-dir]
                  [:cljsbuild :builds :app :compiler :output-to]]
  :ring {:handler toby-site-clj.core/routes} 

  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler {:output-to     "resources/public/js/app.js"
                                        :output-dir    "resources/public/js/out"
                                        :asset-path   "js/out"
                                        :optimizations :whitespace
                                        :pretty-print  true}}}})
