/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlinx.serialization.compiler.diagnostic

import com.intellij.openapi.util.io.JarUtil
import org.jetbrains.kotlin.config.ApiVersion
import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinarySourceElement
import org.jetbrains.kotlinx.serialization.compiler.resolve.SerialEntityNames
import org.jetbrains.kotlinx.serialization.compiler.resolve.getClassFromSerializationPackage
import java.io.File
import java.util.jar.Attributes

object VersionReader {
    data class RuntimeVersions(val implementationVersion: ApiVersion?, val requireKotlinVersion: ApiVersion?) {
        fun currentCompilerMatchRequired(): Boolean {
            val current = requireNotNull(KotlinCompilerVersion.getVersion()?.let(ApiVersion.Companion::parse))
            return requireKotlinVersion == null || requireKotlinVersion <= current
        }
    }

    fun getVersionsFromManifest(runtimeLibraryPath: File): RuntimeVersions {
        val version = JarUtil.getJarAttribute(runtimeLibraryPath, Attributes.Name.IMPLEMENTATION_VERSION)?.let(ApiVersion.Companion::parse)
        val kotlinVersion = JarUtil.getJarAttribute(runtimeLibraryPath, REQUIRE_KOTLIN_VERSION)?.let(ApiVersion.Companion::parse)
        return RuntimeVersions(version, kotlinVersion)
    }

    val MINIMAL_SUPPORTED_VERSION = ApiVersion.parse("1.0-M1")

    private val REQUIRE_KOTLIN_VERSION = Attributes.Name("Require-Kotlin-Version")
    private const val CLASS_SUFFIX = "!/kotlinx/serialization/KSerializer.class"

    fun getVersionsForCurrentModule(module: ModuleDescriptor): RuntimeVersions? {
        val markerClass = module.getClassFromSerializationPackage(SerialEntityNames.KSERIALIZER_CLASS)
        val location = (markerClass.source as? KotlinJvmBinarySourceElement)?.binaryClass?.location ?: return null
        val jarFile = location.removeSuffix(CLASS_SUFFIX)
        if (!jarFile.endsWith(".jar")) return null
        val file = File(jarFile)
        if (!file.exists()) return null
        return getVersionsFromManifest(file)
    }
}