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
package org.jupnp.support.avtransport.callback;

import org.jupnp.controlpoint.ActionCallback;
import org.jupnp.model.action.ActionInvocation;
import org.jupnp.model.meta.Service;
import org.jupnp.model.types.UnsignedIntegerFourBytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christian Bauer - Initial Contribution
 * @author Amit Kumar Mondal - Code Refactoring
 */
public abstract class SetAVTransportURI extends ActionCallback {

    private final Logger logger = LoggerFactory.getLogger(SetAVTransportURI.class);

    protected SetAVTransportURI(Service<?, ?> service, String uri) {
        this(new UnsignedIntegerFourBytes(0), service, uri, null);
    }

    protected SetAVTransportURI(Service<?, ?> service, String uri, String metadata) {
        this(new UnsignedIntegerFourBytes(0), service, uri, metadata);
    }

    protected SetAVTransportURI(UnsignedIntegerFourBytes instanceId, Service<?, ?> service, String uri) {
        this(instanceId, service, uri, null);
    }

    protected SetAVTransportURI(UnsignedIntegerFourBytes instanceId, Service<?, ?> service, String uri,
            String metadata) {
        super(new ActionInvocation<>(service.getAction("SetAVTransportURI")));
        logger.debug("Creating SetAVTransportURI action for URI: {}", uri);
        getActionInvocation().setInput("InstanceID", instanceId);
        getActionInvocation().setInput("CurrentURI", uri);
        getActionInvocation().setInput("CurrentURIMetaData", metadata);
    }

    @Override
    public void success(ActionInvocation invocation) {
        logger.debug("Execution successful");
    }
}
