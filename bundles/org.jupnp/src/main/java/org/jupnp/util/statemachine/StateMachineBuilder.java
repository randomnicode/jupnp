/*
 * Copyright (C) 2011-2025 4th Line GmbH, Switzerland and others
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License Version 1 or later
 * ("CDDL") (collectively, the "License"). You may not use this file
 * except in compliance with the License. See LICENSE.txt for more
 * information.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * SPDX-License-Identifier: CDDL-1.0
 */
package org.jupnp.util.statemachine;

import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author Christian Bauer - Initial Contribution
 * @author Amit Kumar Mondal - Code Refactoring
 */
public class StateMachineBuilder {

    public static <T extends StateMachine> T build(Class<T> stateMachine, Class<?> initialState) {
        return build(stateMachine, initialState, null, null);
    }

    public static <T extends StateMachine> T build(Class<T> stateMachine, Class<?> initialState,
            Class<?>[] constructorArgumentTypes, Object[] constructorArguments) {
        return (T) Proxy.newProxyInstance(stateMachine.getClassLoader(), new Class<?>[] { stateMachine },
                new StateMachineInvocationHandler(Arrays.asList(stateMachine.getAnnotation(States.class).value()),
                        initialState, constructorArgumentTypes, constructorArguments));
    }
}
