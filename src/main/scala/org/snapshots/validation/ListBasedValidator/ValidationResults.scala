package org.snapshots.validation.ListBasedValidator

import ValidationResult._

case class ValidationResults[T](validationResults: Traversable[ValidationResult[T]]) {
  def |@|(res: ValidationResult[T]): ValidationResults[T] =
    copy(validationResults = validationResults ++ Traversable(res))
  
  def +(res: ValidationResult[T]): ValidationResults[T] = |@|(res)
  
  def validate: Traversable[ValidationResult[T]] = validationResults
  def getFails: Traversable[Failure[T]] = validationResults.collect { case f: Failure[T] => f }
  def getErrors: Traversable[T] = validationResults.collect { case Failure(error) => error }
}
object ValidationResults {
  implicit def validationResult2Seq[T](res: ValidationResult[T]): ValidationResults[T] =
    ValidationResults(Seq(res))
}

