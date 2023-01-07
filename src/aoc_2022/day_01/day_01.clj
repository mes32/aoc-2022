(ns aoc-2022.day-01.day-01
  (:gen-class)
  (:require [aoc-2022.utils :refer [read-resource]]))

(defn non-blank-list? [list]
  (-> list first clojure.string/blank? not))

(defn parse-and-sum [list-of-lists]
  (map (fn [list]
         (apply + (map #(Integer/parseInt %) list))) list-of-lists))

(defn day-01 []
 (let [calories-per-elf (->> "day_01.txt"
                             read-resource
                             (partition-by clojure.string/blank?)
                             (filter non-blank-list?)
                             parse-and-sum)
       part1 (reduce max calories-per-elf)
       part2 (->> calories-per-elf
                  (sort >)
                  (take 3)
                  (apply +))]
   (do (println "Part 1:" part1)
       (println "Part 2:" part2))))
