(ns goal-tracker.goals.update
  (:require [clojurewerkz.neocons.rest.cypher :as cy]
            [clojurewerkz.neocons.rest.nodes :as nn]))


(def get-goal-by-id "MATCH (g:Goal) WHERE g.id = {id} RETURN g;")
(def update-goal-by-id "MATCH (g:Goal) WHERE g.id = {id} SET g.description = {description} SET g.start = {start} SET g.end = {end} RETURN g;")

(defn update-goals [conn nodeid start end description]

  (let [
    res (cy/tquery conn get-goal-by-id {:id nodeid})
    goalid (get res "id")
    start (or start (get res "start"))
    end (or end (get res "end"))
    description (or description (get res "description"))]

    (cy/tquery conn update-goal-by-id {:id nodeid :description description :start start :end end})))

  ;(nn/set-property conn (:id (str myid)) :description "Blah")))
  ;(nn/update conn (:id myid) {:id goalid :start start :end end :description description})))