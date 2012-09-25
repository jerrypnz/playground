(ns dataprocessing.io
  (:import (java.nio CharBuffer
                     ByteBuffer)
           (java.nio.charset Charset)
           (java.nio.channels FileChannel$MapMode)
           (java.io FileInputStream
                    InputStreamReader
                    BufferedReader)
           (com.google.common.io LineReader)))

;;;; THE MAPPED LINE SEQ IS DEPRECATED ;;;;;;;;;;;;;

(defn map-text-file 
  "Create a new memory mapped CharBuffer"
  [^String file charset]
  (with-open [channel (.getChannel (FileInputStream. file))]
    (let [charset (Charset/forName charset)
          byte-buf (.map channel
                         FileChannel$MapMode/READ_ONLY
                         0 (.size channel))]
      (.decode charset byte-buf))))

(defn- lazy-line-seq 
  "Create a lazy line sequence of given CharBuffer"
  [^LineReader line-reader]
  (lazy-seq
   (when-let [line (.readLine line-reader)]
     (cons line (lazy-line-seq line-reader)))))

(defn mapped-line-seq
  "Return a lazy sequence of lines backed by a mapped byte buffer"
  ([file] (mapped-line-seq file "UTF-8"))
  ([^String file ^String charset]
     (let [char-buf (map-text-file file charset)
           line-reader (LineReader. char-buf)]
       (lazy-line-seq line-reader))))

(defn- lazy-buffered-line-seq
  "Create a lazy line sequence of given BufferedReader"
  [^BufferedReader reader]
  (lazy-seq
   (if-let [line (.readLine reader)]
     (cons line (lazy-buffered-line-seq reader))
     (.close reader))))

(defn buffered-line-seq 
  "Return a lazy sequence of lines backed by a buffered reader"
  ([file] (buffered-line-seq file "UTF-8"))
  ([^String file ^String charset]
     (let [reader (BufferedReader.
                   (InputStreamReader.
                    (FileInputStream. file)
                    charset))]
       (lazy-buffered-line-seq reader))))
