(ns aoc-2022.day-02.day-02
  (:gen-class)
  (:require [aoc-2022.utils :refer [read-resource]]))

(def decode-choice
  {"A" :rock
   "B" :paper
   "C" :scissors
   "X" :rock
   "Y" :paper
   "Z" :scissors})

(def decode-outcome
  {"X" :lose
   "Y" :draw
   "Z" :win})

(def wins-to
  {:rock     :paper
   :paper    :scissors
   :scissors :rock})

(def loses-to
  (clojure.set/map-invert wins-to))

(defn decode-choices [[p1-choice-raw p2-choice-raw]]
  [(decode-choice p1-choice-raw) (decode-choice p2-choice-raw)])

(defn score-round [[p1-choice p2-choice]]
  (+ (case p2-choice
       :rock 1
       :paper 2
       :scissors 3)
     (cond
       (= p2-choice (wins-to p1-choice)) 6
       (= p2-choice p1-choice) 3
       :else 0)))

(defn decode-strategy [[p1-choice-raw outcome-raw]]
  [(decode-choice p1-choice-raw) (decode-outcome outcome-raw)])

(defn choose-strategy [[p1-choice outcome]]
  (let [p2-choice (case outcome
                    :lose (loses-to p1-choice)
                    :draw p1-choice
                    :win (wins-to p1-choice))]
    [p1-choice p2-choice]))

(defn day-02 []
 (let [strategy-guide (->> "day_02.txt"
                           read-resource
                           (map #(clojure.string/split % #" ")))
       part1 (->> strategy-guide
                  (map decode-choices)
                  (map score-round)
                  (apply +))
       part2 (->> strategy-guide
                  (map decode-strategy)
                  (map choose-strategy)
                  (map score-round)
                  (apply +))]
   (do (println "Part 1:" part1)
       (println "Part 2:" part2))))
