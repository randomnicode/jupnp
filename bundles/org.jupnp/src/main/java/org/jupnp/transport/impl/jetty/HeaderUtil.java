/*
 * Copyright (C) 2011-2024 4th Line GmbH, Switzerland and others
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
package org.jupnp.transport.impl.jetty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.Request;
import org.eclipse.jetty.client.Response;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.io.Content;
import org.jupnp.http.Headers;

/**
 * Converts from/to jUPnP Headers to/from HTTP client (Jetty) header format.
 *
 * @author Christian Bauer
 * @author Victor Toni
 */
public class HeaderUtil {

    private HeaderUtil() {
        // no instance of this class
    }

    /**
     * Add all jUPnP {@link Headers} header information to {@link Request}.
     *
     * @param request to enrich with header information
     * @param headers to be added to the {@link Request}
     */
    public static void add(final Request request, final Headers headers) {
        request.headers(rh -> headers.forEach(rh::add));
    }

    /**
     * Add all jUPnP {@link Headers} header information to
     * {@link org.eclipse.jetty.server.Response}.
     *
     * @param response to enrich with header information
     * @param headers to be added to the {@link org.eclipse.jetty.server.Response}
     */
    public static void add(final HttpServletResponse response, final Headers headers) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            for (final String value : entry.getValue()) {
                response.addHeader(entry.getKey(), value);
            }
        }
    }

    /**
     * Get all header information from {@link Response} jUPnP {@link Headers}.
     *
     * @param response {@link Response}, must not be null
     * @return {@link Headers}, never {@code null}
     */
    public static Headers get(final Response response) {
        final Headers headers = new Headers();
        for (HttpField httpField : response.getHeaders()) {
            headers.put(httpField.getName(), httpField.getValueList());
        }

        return headers;
    }

    /**
     * Get all header information from {@link Request} jUPnP {@link Headers}.
     *
     * @param request {@link Request}, must not be null
     * @return {@link Headers}, never {@code null}
     */
    public static Headers get(final org.eclipse.jetty.server.Request request) {
        final Headers headers = new Headers();
        for (HttpField httpField : request.getHeaders()) {
            headers.put(httpField.getName(), httpField.getValueList());
        }

        return headers;
    }

    /**
     * Get all header information from {@link Request} jUPnP {@link Headers}.
     *
     * @param request {@link Request}, must not be null
     * @return {@link Headers}, never {@code null}
     */
    public static String getContent(final Request request) throws IOException {
        return Content.Source.asString(request.getBody());
    }

    /**
     * Get all header information from {@link Request} jUPnP {@link Headers}.
     *
     * @param request {@link Request}, must not be null
     * @return {@link Headers}, never {@code null}
     */
    public static byte[] getBytes(final Request request) {
        return Content.Source.asByteArrayAsync(request.getBody(), -1).join();
    }

    /**
     * Get all header information from {@link Request} jUPnP {@link Headers}.
     *
     * @param request {@link Request}, must not be null
     * @return {@link Headers}, never {@code null}
     */
    public static byte[] getBytes(final org.eclipse.jetty.server.Request request) throws IOException {
        try (InputStream is = org.eclipse.jetty.server.Request.asInputStream(request)) {

            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final byte[] bytes = new byte[1024];
            int len;
            while (0 < (len = is.read(bytes))) {
                bos.write(bytes, 0, len);
            }

            return bos.toByteArray();
        }
    }
}
