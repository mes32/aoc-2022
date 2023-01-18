(ns aoc-2022.day-04.day-04
  (:gen-class)
  (:require [aoc-2022.utils :refer [read-resource]]))

(defn section-ranges [raw-data]
  (let [digits (clojure.string/split raw-data #"-|,")
        start-1 (-> digits (nth 0) Integer/parseInt)
        end-1 (-> digits (nth 1) Integer/parseInt)
        start-2 (-> digits (nth 2) Integer/parseInt)
        end-2 (-> digits (nth 3) Integer/parseInt)]
    {:start-1 start-1 :end-1 end-1 :start-2 start-2 :end-2 end-2}))

(defn within-range? [value range-start range-end]
  (and (>= value range-start)
       (<= value range-end)))

(defn range-contains-other? [ranges]
  (or (and (within-range? (:start-1 ranges) (:start-2 ranges) (:end-2 ranges))
           (within-range? (:end-1 ranges) (:start-2 ranges) (:end-2 ranges)))
      (and (within-range? (:start-2 ranges) (:start-1 ranges) (:end-1 ranges))
           (within-range? (:end-2 ranges) (:start-1 ranges) (:end-1 ranges)))))

(defn range-contains-at-all? [ranges]
  (or (or (within-range? (:start-1 ranges) (:start-2 ranges) (:end-2 ranges))
          (within-range? (:end-1 ranges) (:start-2 ranges) (:end-2 ranges)))
      (or (within-range? (:start-2 ranges) (:start-1 ranges) (:end-1 ranges))
          (within-range? (:end-2 ranges) (:start-1 ranges) (:end-1 ranges)))))

(defn day-04 []
 (let [ranges (->> "day_04.txt"
                   read-resource
                   (map section-ranges))
       part1 (->> ranges
                  (map range-contains-other?)
                  (remove false?)
                  count)
       part2 (->> ranges
                  (map range-contains-at-all?)
                  (remove false?)
                  count)]
   (do (println "Part 1:" part1)
       (println "Part 2:" part2))))
