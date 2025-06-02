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
package org.jupnp.support.model.dlna;

import java.util.EnumSet;
import java.util.Locale;

/**
 * @author Mario Franco
 * @author Amit Kumar Mondal - Code Refactoring
 */
public class DLNAOperationsAttribute extends DLNAAttribute<EnumSet<DLNAOperations>> {

    public DLNAOperationsAttribute() {
        setValue(EnumSet.of(DLNAOperations.NONE));
    }

    public DLNAOperationsAttribute(DLNAOperations... op) {
        if (op != null && op.length > 0) {
            DLNAOperations first = op[0];
            if (op.length > 1) {
                System.arraycopy(op, 1, op, 0, op.length - 1);
                setValue(EnumSet.of(first, op));
            } else {
                setValue(EnumSet.of(first));
            }
        }
    }

    @Override
    public void setString(String s, String cf) {
        EnumSet<DLNAOperations> value = EnumSet.noneOf(DLNAOperations.class);
        try {
            int parseInt = Integer.parseInt(s, 16);
            for (DLNAOperations op : DLNAOperations.values()) {
                int code = op.getCode() & parseInt;
                if (op != DLNAOperations.NONE && op.getCode() == code) {
                    value.add(op);
                }
            }
        } catch (NumberFormatException numberFormatException) {
            // no need to take any measure
        }

        if (value.isEmpty()) {
            throw new InvalidDLNAProtocolAttributeException("Can't parse DLNA operations integer from: " + s);
        }

        setValue(value);
    }

    @Override
    public String getString() {
        int code = DLNAOperations.NONE.getCode();
        for (DLNAOperations op : getValue()) {
            code |= op.getCode();
        }
        return String.format(Locale.ROOT, "%02x", code);
    }
}
