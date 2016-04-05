package reader

import cats._
import cats.std.all._
import cats.syntax.functor._
import cats.syntax.flatMap._
import cats.syntax.traverse._

import Dao._

class Service(dao: Dao) {

  def getAllBlogsWithComments(): DbOp[Seq[Blog]] = transactional {
    for {
      rawBlogs ← dao.findAllBlogs
      blogs    ← rawBlogs.toList.traverse { blog ⇒
        dao.findComments(blog.id).map { xs ⇒ blog.copy(comments = xs) }
      }
    } yield blogs
  }

}
