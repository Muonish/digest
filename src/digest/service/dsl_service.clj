(ns digest.service.dsl-service
  (:require [digest.dal.users-provider :as user]))

(def userdal (user/->users-provider))

(defn show [klogin login]  ; show :user "123"
  (if (= :user klogin)
      (let [u (.get-item userdal {:email login})]
        (if u
            (let [{id :id role :is_admin} u]
              (str "Username: " login "; Id: " id "; isAdmin: " role))
            (str "No such user: " login)))
      (str "No such command: " klogin)))

(defn delete [klogin login]  ; delete :user "123"
  (if (= :user klogin)
      (let [res (.delete-item userdal {:email login})]
        (if res
            (str "User \"" login "\" was deleted!")
            (str "No such user: " login)))
      (str "No such command: " klogin)))

(defn change [klogin login krole role]  ; change :user "123" :is_admin true
  (if (and (= :user klogin) (= :is_admin krole))
      (let [res (.update-item userdal {:email login :is_admin role})]
        (if res
            (str "User \"" login "\" now is_admin " role)
            (str "No such user: " login)))
      (str "No such command")))

(defn add-brackets [string]
  (str "(digest.service.dsl-service/" string ")"))

(defn gcc "Greate Clojure Compiler"
  [string]
  (try
    (eval (read-string (add-brackets string)))
    (catch Exception e (str "Unknown command"))))