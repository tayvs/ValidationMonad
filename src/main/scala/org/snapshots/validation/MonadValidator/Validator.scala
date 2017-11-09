package org.snapshots.validation.MonadValidator

/**
  *
  * @param func - element validator. Validate one field of Src object
  * @tparam Src - object for validation (campaign)
  * @tparam El  - element for validation (General, Offer etc)
  * @tparam Err - error type
  */
class Validator[Src, El, Err](private val func: (Src, Option[Err]) => (El, Option[Err])) {
  
  def flatMap[M](f: (El) => Validator[Src, M, Err]): Validator[Src, M, Err] =
    Validator[Src, M, Err] { (src: Src, optError: Option[Err]) =>
      val (el, errorOpt) = func(src, optError)
      f(el).func(src, errorOpt)
    }
  
  def map[M](f: (El) => M): Validator[Src, M, Err] =
    Validator[Src, M, Err] { (src: Src, optError: Option[Err]) =>
      val (el, errorOpt) = func(src, optError)
      (f(el), errorOpt)
    }
  
  /** Take src and validate it. Return tuple of valid src, where all invalid fields replace on default, and error
    *
    * @param src       - src for validation
    * @param stopError - Error message, that invoke stop validation, but not return to user
    * @return src with valid or default fields and error if it was
    */
  def validate(src: Src, stopError: Err): (El, Option[Err]) = {
    val (validSrc, errorOpt: Option[Err]) = func(src, None)
    (validSrc, errorOpt.filterNot(_ == stopError))
  }
  
}

object Validator {
  def apply[Src, El, Err](func: (Src, Option[Err]) => (El, Option[Err])): Validator[Src, El, Err] =
    new Validator(func)
}

