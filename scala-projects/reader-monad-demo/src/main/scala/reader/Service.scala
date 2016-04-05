package reader

import cats._
import cats.std.all._
import cats.syntax.cartesian._
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

  def joinBlogsAndComments(blogs: Seq[Blog], comments: Seq[Comment]) = {
    val commentMap = comments.groupBy(_.blogId)
    blogs.map { b ⇒
      commentMap.get(b.id).map(xs ⇒ b.copy(comments = xs)).getOrElse(b)
    }
  }

  def getAllBlogsWithCommentsAlt(): DbOp[Seq[Blog]] = transactional {
    (dao.findAllBlogs |@| dao.findAllComments) map joinBlogsAndComments
  }
}
