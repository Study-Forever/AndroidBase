package com.base.library.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
object ViewBindingUtil {

    /**
     * Activity使用
     */
    fun <VB : ViewBinding> inflateWithGeneric(
        genericOwner: Any,
        layoutInflater: LayoutInflater
    ): VB = withGenericBindingClass(genericOwner) { clz ->
        clz.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB
    }

    /**
     * Fragment使用
     */
    fun <VB : ViewBinding> inflateWithGeneric(
        genericOwner: Any,
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): VB = withGenericBindingClass(genericOwner) { clz ->
        clz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        ).invoke(null, layoutInflater, container, attachToParent) as VB
    }

    private fun <VB : ViewBinding> withGenericBindingClass(
        genericOwner: Any,
        block: (Class<VB>) -> VB
    ): VB {
        val weakOwner = WeakReference(genericOwner)
        val jCLz = weakOwner.get()?.javaClass
        val superClz = jCLz?.superclass
        val genericSuperClass = jCLz?.genericSuperclass
        if (superClz != null && genericSuperClass is ParameterizedType) {
            genericSuperClass.actualTypeArguments.forEach {
                try {
                    return block.invoke(it as Class<VB>)
                } catch (e: Exception) {
                    logger("InflateException") { e.toString() }
                }
            }
        }
        throw IllegalArgumentException("Here need a ViewBinding")
    }
}