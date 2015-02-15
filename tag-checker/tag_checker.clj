(def tag-pattern #"<\/?([A-Z])>")

(defn check-tag-match [line]
  (let [tags (re-seq tag-pattern line)
        is-closing-tag #(.contains % "/")
        [err tag-stack] (reduce (fn [[_ stack] [tag name]]
                                  (if (is-closing-tag tag)
                                    (if-let [expected-name (first stack)]
                                      (if (= name expected-name)
                                        [nil (rest stack)]
                                        (reduced [(str "Expected </" expected-name "> found " tag) []]))
                                      (reduced [(str "Expected # found " tag) []]))
                                    [nil (cons name stack)]))
                                [nil []]
                                tags)]
    (or err (if-let [expected-name (first tag-stack)]
              (str "Expected </" expected-name "> found #")
              "Correctly tagged paragraph"))))


(println (check-tag-match "The following text<C><B>is centred and in boldface</B></C>"))
(println (check-tag-match "<B>This <\\g>is <B>boldface</B> in <<*> a</B> <\\6> <<d>sentence"))
(println (check-tag-match "<B><C> This should be centred and in boldface, but the tags are wrongly nested </B></C>"))
(println (check-tag-match "<B>This should be in boldface, but there is an extra closing tag</B></C>"))
(println (check-tag-match "<B><C>This should be centred and in boldface, but there is a missing closing tag</C>"))
