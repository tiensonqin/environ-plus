(ns environ-plus.core
  (:require [environ.core :as environ]
            [clojure.edn :as edn]
            [clojure.java.io :as io])
  (:import java.io.PushbackReader))

(defn- read-config-file [path]
  (try
    (with-open [r (-> path io/resource io/reader PushbackReader.)]
      (edn/read r))
    (catch Exception e
      (println (str "WARNING: edn-config: " (.getLocalizedMessage e))))))

(defonce ^{:doc "A map of environment variables."}
  env
  (let [config (read-config-file (str "config/" (:environment environ/env) ".edn"))]
    (merge
     config
     environ/env)))
