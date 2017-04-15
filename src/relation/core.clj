(ns relation.core
  (:require [clojure.java.io :as io])
  (use relation.aux)
  )

(defn -main
  [& args]
  (def students {})
  (def relations {})
 )

(defn add-student [name]
  (if (contains? students (keyword name))
    (pr "Already exist")
    (do (def students (assoc students (keyword name) (count students)))
        (def relations (assoc relations (num-to-key (.get students (keyword name))) [])))))

(defn add-relation [name1 name2]
  (let [index1 (.get students (keyword name1))
        index2 (.get students (keyword name2))]
    (if (not (and (contains? students (keyword name1)) (contains? students (keyword name2))))
      (pr "No such name")
      (if (>= (.indexOf ((num-to-key index1) relations) (num-to-key index2)) 0)
        (pr "Already exist")
        (def relations (conj-assoc relations (num-to-key index1) (num-to-key index2)))))))

(defn delete-relation [name1 name2]
  (let [index1 (.get students (keyword name1))
        index2 (.get students (keyword name2))]
    (if (< (.indexOf ((num-to-key index1) relations) (num-to-key index2)) 0)
      (pr "Already deleted")
      (def relations (conj-dissoc relations (num-to-key index1) (num-to-key index2))))))

(defn students-write []
  (with-open [wrtr (io/writer "./data/students.db")]
      (.write wrtr (str students))))

(defn relations-write []
  (with-open [wrtr (io/writer "./data/relations.db")]
    (.write wrtr (str relations))))

(defn students-read []
  (def students (read-string (slurp "./data/students.db"))))


(defn relations-read []
  (def relations (read-string (slurp "./data/relations.db"))))

(defn double-relation [name1 name2]
  (add-relation name1 name2)
  (add-relation name2 name1)
  )

(defn find-name [val]
  (filter (comp #{val} students) (keys students)))

;; (defn relations-export []
;;   (do
;;     (def temp "{")
;;     (for [x relations]
;;       (map (fn [l] (def temp (str temp (clojure.string/replace (str (first x) " -> " l ", ") ":" "")))) (second x)))
;;     (def temp (str (subs temp 0 (- (count temp) 2)) "}" ))
;;     )
;;   )

(defn relations-export-v2 []
(do
 (def temp "{")
 (for [x relations]
   (map (fn [l] (def temp (clojure.string/replace (str temp  (str  (first (find-name  (bigint (subs (str (first x)) 1)))))  " -> " (str (first (find-name  (bigint (subs (str l) 1))))) ", ") ":" ""))) (second x)))
   (def temp (str (subs temp 0 (- (count temp) 2)) "}" ))
))
