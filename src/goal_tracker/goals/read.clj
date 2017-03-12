(ns goal-tracker.goals.read
  (:require [clojurewerkz.neocons.rest.cypher :as cy]))

(def goal-query
  "OPTIONAL MATCH (g:Goal)<-[:OWNS]-(p:Person)
   RETURN g as goal, p as owner;")

(def goal-query-by-owner
  "MATCH (g:Goal)<-[:OWNS]-(p:Person)
   WHERE p.id = {owner}
   RETURN g as goal, p as owner;")

(defn goal-data-extract [{:strs [goal owner]}]
  (let [goal-data (:data goal)
        owner-data (:data owner)]
    (assoc goal-data :owner owner-data)))

(defn get-goals
  [conn owner]
  (let [result (if owner
                 (cy/tquery conn goal-query-by-owner {:owner owner})
                 (cy/tquery conn goal-query))]
    (map goal-data-extract result)))
