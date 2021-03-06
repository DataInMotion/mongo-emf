/*******************************************************************************
 * Copyright (c) 2012 Bryan Hunt.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Bryan Hunt - initial API and implementation
 *******************************************************************************/

package org.eclipselabs.mongoemf.handlers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipselabs.emodeling.UriHandlerProvider;
import org.eclipselabs.emongo.MongoDatabaseProvider;
import org.eclipselabs.mongoemf.InputStreamFactory;
import org.eclipselabs.mongoemf.OutputStreamFactory;

/**
 * @author bhunt
 * 
 */
public class MongoURIHandlerProvider implements UriHandlerProvider
{
	@Override
	public synchronized URIHandler getURIHandler()
	{
		if (uriHandler == null)
			uriHandler = new MongoURIHandlerImpl(mongoDatabaseProviders, inputStreamFactory, outputStreamFactory);

		return uriHandler;
	}

	public void bindMongoDatabaseProvider(MongoDatabaseProvider mongoDatabaseProvider)
	{
		mongoDatabaseProviders.put(mongoDatabaseProvider.getURI(), mongoDatabaseProvider);
	}

	public void unbindMongoDatabaseProvider(MongoDatabaseProvider mongoDatabaseProvider)
	{
		mongoDatabaseProviders.remove(mongoDatabaseProvider.getURI());
	}

	public void bindInputStreamFactory(InputStreamFactory inputStreamFactory)
	{
		this.inputStreamFactory = inputStreamFactory;
	}

	public void bindOutputStreamFactory(OutputStreamFactory outputStreamFactory)
	{
		this.outputStreamFactory = outputStreamFactory;
	}

	private MongoURIHandlerImpl uriHandler;
	private Map<String, MongoDatabaseProvider> mongoDatabaseProviders = new ConcurrentHashMap<String, MongoDatabaseProvider>();
	private InputStreamFactory inputStreamFactory;
	private OutputStreamFactory outputStreamFactory;
}
