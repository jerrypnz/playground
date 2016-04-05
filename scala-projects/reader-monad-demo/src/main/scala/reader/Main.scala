package reader

object Main extends App {

  val dao = new Dao
  val service = new Service(dao)

  println("Reader monad demo.")
  val session = Session("db1")

  val blogs = Dao.runDbOps(session) {
    service.getAllBlogsWithComments()
  }

  println("All blogs with comments from DB: ")
  println(blogs)
}
