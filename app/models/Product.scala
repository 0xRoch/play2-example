package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

object Product {

  val product = {
    get[Long]("id") ~
    get[String]("name") ~
    get[Long]("price") map {
      case id ~ name ~ price =>
        Product(id, name, price)
    }
  }

  def findAll(): List[Product] = {
    DB.withConnection { implicit c =>
      SQL("select * from PRODUCT")
        .as(product *)
    }
  }

  def persist(product: Product) {
    DB.withConnection { implicit c =>
      SQL("insert into PRODUCT (NAME, PRICE) values ({name}, {price})")
        .on(
          'name -> product.name,
          'price -> product.price)
        .executeUpdate()
    }
  }

  def remove(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from PRODUCT where id = {id}")
        .on(
          'id -> id)
        .executeUpdate()
    }
  }

}

case class Product(
  id: Long,
  name: String,
  price: Long)