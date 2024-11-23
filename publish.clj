#!/usr/bin/env bb
;; publish by deploying the content of `public` to origin/gh-pages

(require
 '[clojure.string :as str]
 '[clojure.main :refer [demunge]]
 '[babashka.process :refer [shell]]
 '[babashka.fs :as fs])

(defn fn-name [a-fn]
  (-> a-fn str demunge (str/split #"@") first (str/replace #"babashka." "")))

(defn does [act & args]
  (println (str "[bb] " (fn-name act) " " (reduce #(str %1 " " %2) args)))
  (apply act args))

(def build "public")
(def remote-url (-> (shell {:out :string} "git config --get remote.origin.url") :out str/trim-newline))

(defn clean-build []
  (fs/delete-tree "public/.git" {:force true})
  (does shell "bb quickblog clean")
  (does shell "bb quickblog render"))

(defn publish-target []
  (does shell {:dir build :continue true} "git" "init" "-b" "main")
  (does shell {:dir build} "git" "add" ".")
  (does shell {:dir build :continue true} "git" "commit" "-m" "\"Deploy to GitHub Pages\"")
  (does shell {:dir build} "git" "push" "--force" remote-url "main:gh-pages"))

(println "sending to" remote-url "=> gh-pages")
(clean-build)
(publish-target)
(prn "deployment done!")