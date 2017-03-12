(ns goal-tracker.goal
  (:require [clojurewerkz.neocons.rest.cypher :as cy]))

(def goal-query
  "OPTIONAL MATCH (g:Goal)<-[:OWNS]-(p:Person)
   RETURN g as goal, p as owner;")

(defn goal-data-extract [{:strs [goal owner]}]
  (let [goal-data (:data goal)
        owner-data (:data owner)]
    (assoc goal-data :owner owner-data)))

(defn get-goals
  [conn]
  (let [result (cy/tquery conn goal-query)]
    (map goal-data-extract result)))
