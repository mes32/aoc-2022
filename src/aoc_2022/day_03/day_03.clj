(ns aoc-2022.day-03.day-03
  (:gen-class)
  (:require [aoc-2022.utils :refer [read-resource]]))

(defn new-rucksack [raw-data]
  (let [split-index (/ (count raw-data) 2)
        split-data (split-at split-index raw-data)]
    {:compartment-1 (apply hash-set (nth split-data 0))
     :compartment-2 (apply hash-set (nth split-data 1))}))

(defn contents [rucksack]
  (clojure.set/union (:compartment-1 rucksack) (:compartment-2 rucksack)))

(defn common-item [rucksack]
  (nth (seq (clojure.set/intersection (:compartment-1 rucksack) (:compartment-2 rucksack))) 0))

(defn common-item-group [group]
  (let [rucksack-1 (contents (nth group 0))
        rucksack-2 (contents (nth group 1))
        rucksack-3 (contents (nth group 2))]
    (nth (seq (clojure.set/intersection rucksack-1 rucksack-2 rucksack-3)) 0)))

(defn item-priority [^Character item-type]
  (if (Character/isUpperCase item-type)
    (- (int item-type) 38)
    (- (int item-type) 96)))

(defn day-03 []
 (let [rucksacks (->> "day_03.txt"
                      read-resource
                      (map new-rucksack))
       part1 (->> rucksacks
                  (map common-item)
                  (map item-priority)
                  (apply +))
       part2 (->> rucksacks
                  (partition 3)
                  (map common-item-group)
                  (map item-priority)
                  (apply +))]
   (do (println "Part 1:" part1)
       (println "Part 2:" part2))))
