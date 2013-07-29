(ns dsl-example.sql
  (require [dsl-example.orm :refer [defentity]]))

(defn not-empty [val]
  (not (nil? val)))

(defentity :author
  :id
  :name
  (has-many :post)
  (validates :name not-empty))

(defentity :post
  :id
  :title
  :content
  (has-many :comment)
  (belongs :author)
  (validates :title not-empty)
  (validates :content not-empty))

(defentity :comment
  :id
  :content
  (belongs :post)
  (belongs :author)
  (validates :content not-empty))


