package com.techmonad.repository

import org.slf4j.LoggerFactory
import slick.jdbc.H2Profile

import slick.lifted.ProvenShape

import scala.concurrent.Future

object PersonRepository {

  val logger = LoggerFactory.getLogger(this.getClass)

  /**
    * import H2 driver specific functions. you can import any supported database driver
    */
   import H2Profile.api._

  /**
    * create connection with H2 in-memory database
    */
  private val db: Database = {
    logger.info("Creating test connection ..................................")
    val h2Url = "jdbc:h2:mem:test;MODE=MySql;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1;"
    Database.forURL(url = h2Url, driver = "org.h2.Driver")
  }

  /**
    * It is table query object for access the database table
    *
    */
  private val personTableQuery = TableQuery[PersonTable]

  private def personTableAutoInc: H2Profile.ReturningInsertActionComposer[Person, Int] =
    personTableQuery.returning(personTableQuery.map(_.id))

  def create(person: Person): Future[Int] =
  db.run {
      personTableAutoInc += person
    }


  def update(person: Person): Future[Int] =
    db.run {
      personTableQuery.filter(_.id === person.id.get).update(person)
    }

  def getById(id: Int): Future[Option[Person]] =
    db.run {
      personTableQuery.filter(_.id === id).result.headOption
    }

  def getAll(): Future[List[Person]] =
    db.run {
      personTableQuery.to[List].result
    }

  def delete(id: Int): Future[Int] =
    db.run {
      personTableQuery.filter(_.id === id).delete
    }


  def ddl: Future[Unit] =
    db.run {
      personTableQuery.schema.create
    }

  /**
    *
    * It is functional relational mapping. Each function mapped with database table's column
    *
    */
  class PersonTable(tag: Tag) extends Table[Person](tag, "person") {
    /**
      *
      * Bidirectional mapping between  database row and  person class
      */
    def * : ProvenShape[Person] = (name, email, age, id.?) <> (Person.tupled, Person.unapply)

    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name: Rep[String] = column[String]("name")

    def email: Rep[String] = column[String]("email")

    def age: Rep[Int] = column[Int]("age")

  }


}


case class Person(name: String, email: String, age: Int, id: Option[Int] = None)