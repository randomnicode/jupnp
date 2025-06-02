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
package org.jupnp.support.lastchange;

import java.util.Map;

import org.jupnp.model.types.Datatype;

/**
 * @author Christian Bauer - Initial Contribution
 * @author Amit Kumar Mondal - Code Refactoring
 */
public class EventedValueShort extends EventedValue<Short> {

    public EventedValueShort(Short value) {
        super(value);
    }

    public EventedValueShort(Map.Entry<String, String>[] attributes) {
        super(attributes);
    }

    @Override
    protected Datatype<?> getDatatype() {
        return Datatype.Builtin.I2_SHORT.getDatatype();
    }
}
