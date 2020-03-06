package com.github.dkurata38.spring_web_lib.annotation

import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.servlet.mvc.support.RedirectAttributes

object PRGSupport {
	fun addBindingResultToFlashAttribute(bindingResult: BindingResult, redirectAttributes: RedirectAttributes): RedirectAttributes {
		val objectName = bindingResult.objectName
		val attributeName = temporaryAttributeName(objectName)
		return redirectAttributes.addFlashAttribute(attributeName, bindingResult)
	}

	fun transferBindingFromResultFlashAttributeToModel(formObjectName: String, modelMap: ModelMap): ModelMap {
		val prefix = BindingResult.MODEL_KEY_PREFIX
		val attributeName = prefix + formObjectName
		if (modelMap.containsAttribute(temporaryAttributeName(formObjectName))) {
			return modelMap.addAttribute(attributeName, modelMap.getAttribute(attributeName))
		}
		return modelMap
	}

	private fun temporaryAttributeName(formObjectName: String): String {
		val tmpAttributePrefix = "tmp_"
		val prefix = BindingResult.MODEL_KEY_PREFIX
		return tmpAttributePrefix + prefix + formObjectName
	}
}
