(ns aoc-2022.day-06.day-06
  (:gen-class)
  (:require [aoc-2022.utils :refer [read-resource]]))

(defn first-marker [signal-buffer num-distinct]
  (->> signal-buffer 
       (partition num-distinct 1 signal-buffer)
       (keep-indexed #(when (apply distinct? %2) (+ %1 num-distinct)))
       first))

(defn day-06 []
 (let [signal-buffer (-> "day_06.txt"
                         read-resource
                         first
                         (clojure.string/split #""))
       part1 (first-marker signal-buffer 4)
       part2 (first-marker signal-buffer 14)]
   (do (println "Part 1:" part1)
       (println "Part 2:" part2))))

