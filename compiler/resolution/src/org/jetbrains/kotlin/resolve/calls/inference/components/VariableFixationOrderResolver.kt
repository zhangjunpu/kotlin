/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.resolve.calls.inference.components

import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableTypeConstructor
import org.jetbrains.kotlin.types.AbstractTypeChecker
import org.jetbrains.kotlin.types.model.TypeConstructorMarker

class VariableFixationOrderResolver(private val resultTypeResolver: ResultTypeResolver) {
    interface Context : KotlinConstraintSystemCompleter.Context

    fun resolveByResultTypeSpecificityIfNeeded(
        context: Context,
        variableForFixationCandidate: VariableFixationFinder.VariableForFixation
    ): VariableFixationFinder.VariableForFixation {
        if (variableForFixationCandidate.variablesWithSameReadiness.isNullOrEmpty())
            return variableForFixationCandidate

        val variableWithMaxSpecificityOfResultType =
            context.resolveByResultTypeSpecificity(variableForFixationCandidate.variablesWithSameReadiness)

        if (variableWithMaxSpecificityOfResultType == variableForFixationCandidate.variable)
            return variableForFixationCandidate

        return variableForFixationCandidate.copy(variable = variableWithMaxSpecificityOfResultType)
    }

    private fun Context.getRelatedVariables(
        current: TypeConstructorMarker,
        requiredToRelate: Set<TypeConstructorMarker>,
        visitedVariables: MutableSet<TypeConstructorMarker>
    ): Boolean {
        val variableWithConstraints = notFixedTypeVariables[current] ?: return false

        visitedVariables.add(current)

        if (requiredToRelate.all { it in visitedVariables })
            return true

        for (constraint in variableWithConstraints.constraints) {
            val constraintTypeConstructor = constraint.type.typeConstructor()
            if (constraintTypeConstructor !is TypeVariableTypeConstructor || constraintTypeConstructor in visitedVariables)
                continue

            if (getRelatedVariables(constraintTypeConstructor, requiredToRelate, visitedVariables))
                return true
        }

        return false
    }

    private fun Context.getRelatedVariables(base: TypeConstructorMarker, requiredToRelate: Set<TypeConstructorMarker>) =
        mutableSetOf<TypeConstructorMarker>()
            .also { getRelatedVariables(base, requiredToRelate, it) }
            .filter { it in requiredToRelate }
            .toSet()

    private fun Context.resolveByResultTypeSpecificity(candidates: Set<TypeConstructorMarker>): TypeConstructorMarker {
        var groupOfRelatedVariables: Set<TypeConstructorMarker>? = null

        for (candidate in candidates) {
            val nextGroupOfRelatedVariables = getRelatedVariables(candidate, candidates)
            if (nextGroupOfRelatedVariables.size >= 2) {
                groupOfRelatedVariables = nextGroupOfRelatedVariables
                break
            }
        }

        if (groupOfRelatedVariables == null) {
            return candidates.first()
        }

        val relatedVariablesWithResultTypes = groupOfRelatedVariables.map {
            it to resultTypeResolver.findResultType(
                this, notFixedTypeVariables.getValue(it), TypeVariableDirectionCalculator.ResolveDirection.UNKNOWN
            )
        }

        val variableWithMaxSpecificityOfResultType = relatedVariablesWithResultTypes.firstOrNull { (_, resultType) ->
            relatedVariablesWithResultTypes.all { (_, anotherResultType) ->
                AbstractTypeChecker.isSubtypeOf(this, resultType, anotherResultType)
            }
        }?.first

        return variableWithMaxSpecificityOfResultType ?: candidates.first()
    }
}