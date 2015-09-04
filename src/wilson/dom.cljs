(ns wilson.dom
  "Tools for building DOMs."
  (:require [wilson.utils :refer [capitalize]]
            [clojure.string :as string]))

(defn table
  "Creates a table displaying the keys in the given rows of data."
  ([keys rows {:keys [row->cls]}]
   [:table {:class "table table-responsive table-hover"}
    [:thead
     (into [:tr]
           (for [k keys]
             [:th (capitalize k)]))]
    (into [:tbody]
          (for [row rows]
            (into [:tr
                   {:class (row->cls row)}]
                  (for [k keys]
                    [:td (get row k)]))))])
  ([keys rows]
   (table keys rows {:row->cls :wilson/row-class})))

(defn label
  "Creates a pretty Bootstrap label."
  [style text]
  [:span
   {:class (str "label label-" style)}
   text])

(defn alert
  "Creates a Bootstrap alert."
  [style text]
  [:div {:class (str "alert alert-" (name style))
         :role "alert"}
   text])

(defn ^:private many-elems
  [maybe-elems]
  (if (keyword? (first maybe-elems))
    [maybe-elems]
    maybe-elems))

(defn panel
  "Creates a Bootstrap panel."
  [header contents]
  [:div {:class "panel panel-default"}
   (into [:div {:class "panel-heading"}] (many-elems header))
   (into [:div {:class "panel-body"}] (many-elems contents))])

(defn button
  "Creates a Bootstrap-styled button."
  [text on-click]
  [:a {:class "btn btn-default" :on-click on-click} text])

(defn merge-attrs
  "Merge DOM element attributes.

  Classes get merged. For other attrs, last one wins."
  [& ms]
  (let [merged (apply merge ms)
        classes (string/join " " (keep :class ms))]
    (if (seq classes)
      (assoc merged :class classes)
      merged)))

(defn with-attrs
  "Adds some attrs to an element."
  [attrs elem]
  (update elem 1 merge-attrs attrs))

(defn with-class
  "Adds a class to an element."
  [cls elem]
  (with-attrs {:class cls} elem))

(defn icon
  "Creates a Glyphicon.

  `type` should be a glyphicon name."
  [type]
  [:span {:class (str "glyphicon glyphicon-" (name type))
          :aria-hidden true}])
