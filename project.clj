(defproject environ-plus "0.1.7"
  :description "Core environ-plus library for use in applications"
  :url "https://github.com/tiensonqin/environ-plus"
  :scm {:dir ".."}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[environ "1.0.0"]]
  :aot :all
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.6.0"]]}})
