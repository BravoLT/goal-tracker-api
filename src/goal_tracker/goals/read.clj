(ns goal-tracker.goals.read
  (:require [clojurewerkz.neocons.rest.cypher :as cy]))

(def goal-query
  "OPTIONAL MATCH (g:Goal)<-[:OWNS]-(p:Person)
   RETURN g as goal, p as owner;")

(def goal-query-by-id
  "OPTIONAL MATCH (g:Goal)<-[:OWNS]-(p:Person)
   WHERE g.id = {id}
   RETURN g as goal, p as owner;")

(def goal-query-by-owner
  "MATCH (g:Goal)<-[:OWNS]-(p:Person)
   WHERE p.id = {owner}
   RETURN g as goal, p as owner;")

(defn extract-node-id [node]
  (get-in node [:metadata :id]))

(defn goal-data-extract [{:strs [goal owner]}]
  (let [owner-data (:data owner)
        goal-data (:data goal)]
    (assoc goal-data :owner owner-data)))

(defn get-goals
  [conn owner id]
  (let [result (cond
                 id (cy/tquery conn goal-query-by-id {:id id})
                 owner (cy/tquery conn goal-query-by-owner {:owner owner})
                 :else (cy/tquery conn goal-query))]
    (->> result (map goal-data-extract) (filter :id))))
