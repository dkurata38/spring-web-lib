package com.github.dkurata38.spring_web_lib.prg_pattern

import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.servlet.mvc.support.RedirectAttributes

interface PRGSupport {
	companion object {
		private const val tmpAttributeName = "errors"
	}

	fun addBindingResultToFlashAttribute(bindingResult: BindingResult, redirectAttributes: RedirectAttributes): RedirectAttributes {
		return redirectAttributes.addFlashAttribute(tmpAttributeName, bindingResult)
	}

	fun transferBindingFromResultFlashAttributeToModel(model: Model) {
		val modelMap = model.asMap()
		if (modelMap.containsKey(tmpAttributeName)) {
			val bindingResult = modelMap[tmpAttributeName] as BindingResult
			model.addAttribute(bindingResult.modelAttributeName(), bindingResult)
		}
	}

	/**
	 * POSTアクション内リダイレクト時にflashAttributeに追加したBindingResultを取得する
	 * @param model model
	 * @return return null if model has no bindingResult
	 */
	fun getBindingResultFromRedirectAttribute(model: Model): BindingResult? {
		return model.asMap()[tmpAttributeName]
				?.let { it as BindingResult }
	}

	/**
	 * POSTアクション内リダイレクト時にflashAttributeに追加したBindingResultを取得する
	 * @param modelMap modelMap
	 * @return return null if model has no bindingResult
	 */
	fun getBindingResultFromRedirectAttribute(modelMap: ModelMap): BindingResult? {
		return modelMap[tmpAttributeName]
				?.let { it as BindingResult }
	}

	private fun BindingResult.modelAttributeName(): String {
		return BindingResult.MODEL_KEY_PREFIX + this.objectName
	}
}
