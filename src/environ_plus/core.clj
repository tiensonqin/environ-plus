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
  (let [environment (if (:environment environ/env) (:environment environ/env) "development")
        config (-> (read-config-file (format "config/%s.edn" environment))
                   (assoc :environment (keyword environment)))]
    (merge
     config
     environ/env)))
