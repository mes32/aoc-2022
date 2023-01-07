(ns aoc-2022.day-01.day-01
  (:gen-class)
  (:require [aoc-2022.utils :refer [read-resource]]))

(defn non-blank-list? [list]
  (-> list first clojure.string/blank? not))

(defn calories-per-elf [list]
  (map (fn [list]
       (apply + (map #(Integer/parseInt %) list)))))

(defn day-01 []
 (let [part1 (->> "day_01.txt"
                  read-resource
                  (partition-by clojure.string/blank?)
                  (filter non-blank-list?)
                  (map (fn [list]
                         (apply + (map #(Integer/parseInt %) list))))
                  (reduce max))
       part2 (->> "day_01.txt"
                  read-resource
                  (partition-by clojure.string/blank?)
                  (filter non-blank-list?)
                  (map (fn [list]
                         (apply + (map #(Integer/parseInt %) list))))
                  (sort >)
                  (take 3)
                  (apply +))]
   (do (println "Part 1: " part1)
       (println "Part 2: " part2))))
