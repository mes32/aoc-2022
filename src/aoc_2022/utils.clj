(ns aoc-2022.utils
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(defn read-resource [filename]
  (let [pathname (str "resources/" filename)]
    (string/split-lines (slurp pathname))))
