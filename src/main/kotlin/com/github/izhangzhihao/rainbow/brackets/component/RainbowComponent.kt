package com.github.izhangzhihao.rainbow.brackets.component

import com.github.izhangzhihao.rainbow.brackets.settings.RainbowSettings
import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.extensions.PluginId

class RainbowComponent : ApplicationComponent {
    var updated: Boolean = false

    override fun initComponent() {
        val settings = RainbowSettings.instance
        updated = getPlugin()?.version != settings.version
        if (updated) {
            settings.version = getPlugin()!!.version
        }
        if (!settings.isRainbowifyHTMLInsideJS) {
            EditorColorsManager
                    .getInstance()
                    .globalScheme
                    .setAttributes(createTextAttributesKey("HTML_CODE"), TextAttributes())
            settings.isRainbowifyHTMLInsideJS = true
        }
    }

    companion object {
        val instance: RainbowComponent
            get() = ApplicationManager.getApplication().getComponent(RainbowComponent::class.java)

        private fun getPlugin(): IdeaPluginDescriptor? = PluginManager.getPlugin(
                PluginId.getId("izhangzhihao.rainbow.brackets"))
    }
}