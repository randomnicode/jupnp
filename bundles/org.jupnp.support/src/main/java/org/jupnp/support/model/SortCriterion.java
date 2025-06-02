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
package org.jupnp.support.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian Bauer - Initial Contribution
 * @author Amit Kumar Mondal - Code Refactoring
 */
public class SortCriterion {

    protected final boolean ascending;
    protected final String propertyName;

    public SortCriterion(boolean ascending, String propertyName) {
        this.ascending = ascending;
        this.propertyName = propertyName;
    }

    public SortCriterion(String criterion) {
        this(criterion.startsWith("+"), criterion.substring(1));
        if (!(criterion.startsWith("-") || criterion.startsWith("+"))) {
            throw new IllegalArgumentException("Missing sort prefix +/- on criterion: " + criterion);
        }
    }

    public boolean isAscending() {
        return ascending;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public static SortCriterion[] valueOf(String s) {
        if (s == null || s.isEmpty()) {
            return new SortCriterion[0];
        }
        List<SortCriterion> list = new ArrayList<>();
        String[] criteria = s.split(",");
        for (String criterion : criteria) {
            list.add(new SortCriterion(criterion.trim()));
        }
        return list.toArray(new SortCriterion[list.size()]);
    }

    public static String toString(SortCriterion[] criteria) {
        if (criteria == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (SortCriterion sortCriterion : criteria) {
            sb.append(sortCriterion.toString()).append(",");
        }
        if (sb.toString().endsWith(",")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ascending ? "+" : "-");
        sb.append(propertyName);
        return sb.toString();
    }
}
