(defproject wilson "0.18.0-SNAPSHOT"
  :description "Opinionated Reagent bindings for Bootstrap components."
  :url "https://www.github.com/racksec/wilson"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring-server "0.4.0"]
                 [ring/ring-devel "1.4.0"]
                 [cljsjs/react "15.0.1-1"]
                 [reagent "0.5.1"]
                 [ring "1.4.0"]
                 [ring/ring-defaults "0.2.0"]
                 [prone "1.1.1"]
                 [compojure "1.5.0"]
                 [hiccup "1.0.5"]
                 [environ "1.0.2"]
                 [org.clojure/clojurescript "1.8.51" :scope "provided"]
                 [secretary "1.2.3"]]

  :plugins [[lein-environ "1.0.2"]
            [lein-auto "0.1.2"]
            [lein-asset-minifier "0.2.8"]]

  ;; HACK: https://github.com/technomancy/leiningen/issues/1940
  :aliases {"test" ["test" "wilson.utils-test"]}

  :ring {:handler wilson.handler/app}

  :min-lein-version "2.5.0"

  :clean-targets
  ^{:protect false} [[:cljsbuild :builds :app :compiler :output-dir]
                     [:cljsbuild :builds :app :compiler :output-to]]

  :minify-assets {:assets
                  {"resources/public/css/site.min.css"
                   "resources/public/css/site.css"}}

  :cljsbuild {:builds {:app {:source-paths ["src/"]
                             :compiler {:output-to "resources/public/js/app.js"
                                        :output-dir "resources/public/js/out"
                                        :asset-path "js/out"
                                        :optimizations :none
                                        :pretty-print true}}}}

  ;; :repl-options {:init-ns figwheel-sidecar.repl-api
  ;;                :init (figwheel-sidecar.repl-api/cljs-repl)}

  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]
                        [leiningen-core "2.6.1"]
                        [lein-figwheel "0.5.2"]
                        [org.clojure/tools.nrepl "0.2.12"]
                        [pjstadig/humane-test-output "0.8.0"]
                        [doo "0.1.6"]]
         :source-paths ["env/dev/clj"]
                   [lein-doo "0.1.1-SNAPSHOT"]]
         :plugins [[lein-figwheel "0.5.2"]
                   [lein-cljsbuild "1.1.3"]
         :injections [(require 'pjstadig.humane-test-output)
                      (pjstadig.humane-test-output/activate!)]
         :figwheel {:http-server-root "public"
                    :server-port 3449
                    :nrepl-port 7002
                    :css-dirs ["resources/public/css"]
                    :ring-handler wilson.handler/app}
         :env {:dev true}
         :cljsbuild {:builds {:app {:source-paths ["env/dev/cljs"]
                                    :compiler {:main "wilson.dev"
                                               :source-map true}}
                              :test {:source-paths ["src/"  "test/"]
                                     :compiler {:output-to "target/test.js"
                                                :optimizations :whitespace
                                                :pretty-print true}}}}}
   :uberjar {:aot :all}})
