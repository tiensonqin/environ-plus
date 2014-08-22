(ns environ-plus.core
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn- keywordize [s]
  (-> (str/lower-case s)
      (str/replace "_" "-")
      (str/replace "." "-")
      (keyword)))

(defn- sanitize [k]
  (let [s (keywordize (name k))]
    (if-not (= k s) (println "Warning: environ-plus key " k " has been corrected to " s))
    s))

(defn- read-system-env []
  (->> (System/getenv)
       (map (fn [[k v]] [(keywordize k) v]))
       (into {})))

(defn- read-system-props []
  (->> (System/getProperties)
       (map (fn [[k v]] [(keywordize k) v]))
       (into {})))

(defn read-file [file]
  (let [env-file (io/file file)]
    (if (.exists env-file)
      (into {} (for [[k v] (read-string (slurp env-file))]
                 [(sanitize k) v])))))

(defn read-resource [resource]
  (try (let [env-file (io/resource resource)]
         (into {} (for [[k v] (read-string (slurp env-file))]
                    [(sanitize k) v])))
       (catch Exception e
         nil)))

(defn get-environment
  [envs]
  (if-let [environment (:environment envs)]
    environment
    "local"))

(defonce ^{:doc "A map of environment variables."}
  env
  (let [envs (read-system-env)
        props (read-system-props)
        environment (get-environment envs)
        config (read-resource (str "config/" environment ".clj"))]
    (merge
     config
     (assoc envs :environment (keyword environment))
     props)))
