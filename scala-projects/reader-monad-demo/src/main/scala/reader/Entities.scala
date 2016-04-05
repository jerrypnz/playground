package reader

case class Blog(id: Int, title: String, content: String, comments: Seq[Comment] = Seq.empty)

case class Comment(id: Int, content: String, by: String, blogId: Int)
