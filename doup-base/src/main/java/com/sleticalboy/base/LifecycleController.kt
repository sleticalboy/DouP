package com.sleticalboy.base

/**
 * Created on 18-1-30.
 *
 * @author sleticalboy
 * @version 1.0
 * @description
 */
class LifecycleController private constructor() {
    
    var lifecycleCallback: LifecycleCallback? = null

    companion object {

        private val CONTROLLER = LifecycleController()

        val instance: LifecycleController
            get() {
                return CONTROLLER
            }
    }
}
