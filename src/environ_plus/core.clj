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

(defn- read-env-file []
  (let [env-file (io/file ".lein-env")]
    (if (.exists env-file)
      (into {} (for [[k v] (read-string (slurp env-file))]
                 [(sanitize k) v])))))

(defn- read-conf-file []
  (let [env-file (io/file "conf/production.clj")]
    (if (.exists env-file)
      (into {} (for [[k v] (read-string (slurp env-file))]
                 [(sanitize k) v])))))

(defonce ^{:doc "A map of environ-plusment variables."}
  env
  (let [confs [(read-env-file)
               (read-conf-file)]
        env-confs (read-system-env)
        confs (if (= "development" (:environ-plusment env-confs)) (reverse confs) confs)]
    (merge
     (first confs)
     (second confs)
     env-confs
     (read-system-props))))
