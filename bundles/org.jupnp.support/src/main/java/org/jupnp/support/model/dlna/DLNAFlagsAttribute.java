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
public class DLNAFlagsAttribute extends DLNAAttribute<EnumSet<DLNAFlags>> {

    public DLNAFlagsAttribute() {
        setValue(EnumSet.noneOf(DLNAFlags.class));
    }

    public DLNAFlagsAttribute(DLNAFlags... flags) {
        if (flags != null && flags.length > 0) {
            DLNAFlags first = flags[0];
            if (flags.length > 1) {
                System.arraycopy(flags, 1, flags, 0, flags.length - 1);
                setValue(EnumSet.of(first, flags));
            } else {
                setValue(EnumSet.of(first));
            }
        }
    }

    @Override
    public void setString(String s, String cf) {
        EnumSet<DLNAFlags> value = EnumSet.noneOf(DLNAFlags.class);
        try {
            int parseInt = Integer.parseInt(s.substring(0, s.length() - 24), 16);
            for (DLNAFlags op : DLNAFlags.values()) {
                int code = op.getCode() & parseInt;
                if (op.getCode() == code) {
                    value.add(op);
                }
            }
        } catch (Exception e) {
            // not required
        }

        if (value.isEmpty()) {
            throw new InvalidDLNAProtocolAttributeException("Can't parse DLNA flags integer from: " + s);
        }

        setValue(value);
    }

    @Override
    public String getString() {
        int code = 0;
        for (DLNAFlags op : getValue()) {
            code |= op.getCode();
        }
        return String.format(Locale.ROOT, "%08x%024x", code, 0);
    }
}
