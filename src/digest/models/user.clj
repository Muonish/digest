(ns digest.models.user)

(defrecord user-record
  [id  
   name
   email
   password
   is_admin])
