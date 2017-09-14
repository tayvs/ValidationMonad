package org.snapshots.validation

object ValidatorFactory {
  
  /** Create validation function
    * If was error then return default value
    * otherwise validate element and if validation return error, then return error and default value
    * otherwise return origin value and None instead error
    *
    * @param getFromSrc            - function for earn validate value from origin object
    * @param elementValidationFunc - function for field validation
    * @param defaultValue          - default value, that will be return if field not valid
    * @tparam Src - origin object type
    * @tparam El  - validation field type
    * @tparam Err - error message type
    * @return
    */
  def generateValidationFunc[Src, El, Err](
    getFromSrc: Src => El,
    elementValidationFunc: (El) => Option[Err],
    defaultValue: El
  ): (Src, Option[Err]) => (El, Option[Err]) = (src: Src, errorOpt: Option[Err]) => {
    val el: El = getFromSrc(src)
    
    errorOpt orElse elementValidationFunc(el) match {
      case Some(error) => (defaultValue, Some(error))
      case None => (el, None)
    }
  }
  
  /** Create validator for specific field into src object
    *
    * @param getFromSrc            - function for earn validate value from origin object
    * @param elementValidationFunc - function for field validation
    * @param defaultValue          - default value, that will be return if field not valid
    * @tparam Src - origin object type
    * @tparam El  - validation field type
    * @tparam Err - error message type
    * @return validator for specific field from origin object
    */
  def createValidator[Src, El, Err](
    getFromSrc: Src => El,
    elementValidationFunc: (El) => Option[Err],
    defaultValue: El
  ): Validator[Src, El, Err] = Validator[Src, El, Err](
    generateValidationFunc(getFromSrc, elementValidationFunc, defaultValue)
  )
  
}
