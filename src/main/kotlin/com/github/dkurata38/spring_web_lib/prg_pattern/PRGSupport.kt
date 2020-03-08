package com.github.dkurata38.spring_web_lib.prg_pattern

import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * Spring MVCのControllerでPRGパターンを簡単に実装するためのユーティリティ.
 * bindingResultをPOST handler methodからリダイレクト先のGET handler methodのmodelに受け渡す関数が定義されている.
 * Spring MVCのControllerにPRGSupportインターフェースを継承させることで利用可能になる.
 */
interface PRGSupport {
	companion object {
		private const val tmpAttributeName = "errors"
	}

	/**
	 * bindingResultをflashAttributeに追加するメソッド.
	 * 引数のredirectAttributesに対して[RedirectAttributes#addFlashAttribute]を実行する.
	 * redirect先で[transferBindingFromResultFlashAttributeToModel]を呼び出すと、bindingResultをmodelに詰め替えられる.
	 * redirect先で[getBindingResultFromRedirectAttribute]を呼び出すと、bindingResultを取得することができる.
	 *
	 * @param bindingResult
	 * @param redirectAttributes
	 *
	 * @return redirectAttributes bindingResultを追加したflashAttributes.
	 */
	fun addBindingResultToFlashAttributes(bindingResult: BindingResult, redirectAttributes: RedirectAttributes): RedirectAttributes {
		return redirectAttributes.addFlashAttribute(tmpAttributeName, bindingResult)
	}

	/**
	 * [addBindingResultToFlashAttributes]でflashAttributeに追加したbindingResultをModelに詰め替えるメソッド.
	 * @param model
	 *
	 * @return model
	 */
	fun transferBindingFromResultFlashAttributeToModel(model: Model): Model {
		return model.getAttribute(tmpAttributeName)
				?.let { it as BindingResult }
				?.let { model.addAttribute(it.modelAttributeName(), it) }
				?: model
	}

	/**
	 * [addBindingResultToFlashAttributes]でflashAttributeに追加したbindingResultをmodelMapに詰め替えるメソッド.
	 * @param modelMap
	 *
	 * @return modelMap
	 */
	fun transferBindingFromResultFlashAttributeToModel(modelMap: ModelMap): ModelMap {
		return modelMap.getAttribute(tmpAttributeName)
				?.let { it as BindingResult }
				?.let { modelMap.addAttribute(it.modelAttributeName(), it) }
				?: modelMap
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

	/**
	 * Springが管理しているBindingResultのModelAttributeNameを取得する.
	 * この名前でbindingResultにmodelを追加すると、JSPの&lt;form:errors/&gt;やThymeleafのth:errorsでエラーメッセージを参照できる.
	 *
	 * @return BindingResultのModelAttributeName
	 */
	fun BindingResult.modelAttributeName(): String {
		return BindingResult.MODEL_KEY_PREFIX + this.objectName
	}
}
