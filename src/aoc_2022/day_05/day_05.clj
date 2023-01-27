(ns aoc-2022.day-05.day-05
  (:gen-class)
  (:require [aoc-2022.utils :refer [read-resource]]))

(defn push-to-stacks! [stacks n c]
  (swap! stacks 
         (fn [s]
           (update-in s [n] #(conj % c)))))

(defn init-stacks [raw-data]
  (let [reversed-data (reverse raw-data)
        num-stacks (-> reversed-data (nth 1) (clojure.string/split #" ") last Integer/parseInt)
        stacks (atom (vec (repeat num-stacks '())))
        crates-data (drop 2 reversed-data)]
    (do 
       (dorun (map (fn [crates-row]
                     (doseq [i (range 0 num-stacks)]
                       (let [crate-index (-> i (* 4) (+ 1))
                             char-at-index (get crates-row crate-index)]
                         (when (not= char-at-index \space)
                           (push-to-stacks! stacks i char-at-index)))))
                   crates-data))
      @stacks)))

(defn get-instruction [row]
  (let [raw-ints (-> row (clojure.string/split #"move | from | to "))]
    {:count (-> raw-ints (nth 1) Integer/parseInt)
     :from (-> raw-ints (nth 2) Integer/parseInt)
     :to (-> raw-ints (nth 3) Integer/parseInt)}))

(defn get-top-set [stacks])

(defn day-05 []
 (let [stacks (->> "day_05.txt"
                   read-resource
                   (filter #(not (clojure.string/starts-with? % "move")))
                   init-stacks)
       instructions (->> "day_05.txt"
                         read-resource
                         (filter #(clojure.string/starts-with? % "move"))
                         (map get-instruction))
       part1 nil #_(->> ranges
                  (map range-contains-other?)
                  (remove false?)
                  count)
       part2 nil #_(->> ranges
                  (map range-contains-at-all?)
                  (remove false?)
                  count)]
   (do (println "Part 1:" stacks)
       #_(println "Part 2:" instructions))))
