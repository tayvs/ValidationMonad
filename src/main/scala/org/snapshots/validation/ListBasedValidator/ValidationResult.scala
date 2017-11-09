package org.snapshots.validation.ListBasedValidator

sealed trait ValidationResult[+T]

object ValidationResult {
  case object Success extends ValidationResult[Nothing]
  case class Failure[+T](error: T) extends ValidationResult[T]
  
  implicit class ValidationValueBuilder[T](value: T) {
    def success: Success.type = ValidationResult.Success
    def failure: Failure[T] = ValidationResult.Failure[T](value)
  }
  implicit class func2ValidationValue(validationRes: Boolean) {
    def validateWithError[T](error: T): ValidationResult[T] = if (validationRes) Success else Failure(error)
  }
  
}
