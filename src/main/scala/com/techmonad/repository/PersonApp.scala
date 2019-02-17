package com.techmonad.repository

import org.slf4j.LoggerFactory

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object PersonApp extends App {
  val logger = LoggerFactory.getLogger(this.getClass)

  // create schema into h2 database
  PersonRepository.ddl.waitTillCompletion

  // Get person list without inserting
  val emptyPersonList = PersonRepository.getAll().waitTillCompletion

  logger.info("Person list before insertion " + emptyPersonList)

  // insert into database
  val person = Person("bob", "bob@gmail.com", 21)
  PersonRepository.create(person).waitTillCompletion

  // Get peron list
  val personList = PersonRepository.getAll().waitTillCompletion
  logger.info("Person list after inserting one record " + personList)


  //delete person
  PersonRepository.delete(1).waitTillCompletion

  // should be empty
  logger.info("Person list after deleting person " + PersonRepository.getAll().waitTillCompletion)


  /**
    * Utility class to get value from future
    * only used for testing. Not recommended for production
    */
  implicit class Waiting[T](val f: Future[T]) {
    def waitTillCompletion: T = {
      Await.result(f, 10 seconds) // It is blocking :)
    }
  }

}
