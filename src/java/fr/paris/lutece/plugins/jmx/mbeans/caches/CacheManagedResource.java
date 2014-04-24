/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.jmx.mbeans.caches;

import fr.paris.lutece.util.jmx.mbeans.ManagedResource;
import fr.paris.lutece.portal.service.cache.CacheableService;


/**
 * CacheManagedResource
 */
public class CacheManagedResource implements ManagedResource
{
    private static final String MBEAN_NAME_CACHES = "Lutece:type=Caches,name=";
    private CacheableService _cs;

    /**
     * Constructor
     * @param cs The cache service
     */
    CacheManagedResource( CacheableService cs )
    {
        _cs = cs;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getName(  )
    {
        return MBEAN_NAME_CACHES + _cs.getName(  );
    }
    
    /**
     * The size (the number of object in the cache)
     * @return The size
     */
    public int getSize()
    {
        return _cs.getCacheSize();
    }
    
    /**
     * The Infos
     * @return The infos
     */
    public String getInfos()
    {
        return _cs.getInfos();
    }
    
    /**
     * Return the memory size
     * @return the memory size
     */
    public long getMemorySize()
    {
        return _cs.getMemorySize();
    }
    
    /**
     * Return the cache status
     * @return the cache status
     */
    public boolean isEnabled()
    {
        return _cs.isCacheEnable();
    }
}
