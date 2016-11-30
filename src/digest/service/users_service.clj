(ns digest.service.users-service
  (:require [digest.dal.users-provider :as user]))

(def userdal (user/->users-provider))

(defn sign-in [{{:keys [email password] :as user} :params session :session} error success]
  (let [{id :id stored-pass :password} (.get-item userdal {:email email})]
    (if (and stored-pass (= password stored-pass))
      (-> (success) (assoc :session (assoc session :user_id id :email email)))
      (error))))
